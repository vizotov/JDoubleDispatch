/*
 * The MIT License (MIT)
 *
 *  Copyright (c) 2018 Vladimir Izotov
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included
 *  in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package su.izotov.java.ddispatch;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiFunction;
import ru.vyarus.java.generics.resolver.GenericsResolver;
import ru.vyarus.java.generics.resolver.context.GenericsContext;

/**
 * Double dispatch capability for method call.
 * To correctly define the return class, you must subclass this class using all type parameters
 * @param <M> Type of master object
 * @param <G> Type of guest object.
 * @param <R> Type of returned object. All methods, which return result is
 * not assignable to this type, will be ignored
 * <p>Created with IntelliJ IDEA.</p>
 * @author Vladimir Izotov
 */
public class Dispatch<M, G, R> {

  private final String methodName;
  private final M master;
  private final G guest;
  private final BiFunction<M, G, R> defaultMethod;

  protected Dispatch(final M master,
                     final G guest,
                     final String methodName,
                     final BiFunction<M, G, R> defaultMethod) {
    this.master = master;
    this.guest = guest;
    this.methodName = methodName;
    this.defaultMethod = defaultMethod;
  }

  /**
   * The conversation of master and guest by selected method. If method is found, then
   * returns it's result, otherwise calls the default method and returns it`s result
   * @return result of the method evaluation
   * @throws MethodAmbiguouslyDefinedException there is more than one method found
   */
  public final R invoke() throws
                          MethodAmbiguouslyDefinedException {
    return this.resultFunction()
               .apply(this.master,
                      this.guest);
  }

  /**
   * the result function for delayed start
   * @return the function
   * @throws MethodAmbiguouslyDefinedException more than one method is found
   */
  @SuppressWarnings("WeakerAccess")
  public final ResultFunction<M, G, R> resultFunction() throws
                                                  MethodAmbiguouslyDefinedException {
    final MasterClass masterClass = new MasterClassImpl(this.master.getClass());
    final GuestClass guestClass = new GuestClassImpl(this.guest.getClass());
    final GenericsContext context = GenericsResolver.resolve(this.getClass())
                                                    .type(Dispatch.class);
    // final Class<?> masterClassRestriction = context.generics().get(0); // Class M for right method searching
    // final Class<?> guestClassRestriction = context.generics().get(1); // Class G for right method searching
    final ReturnClass returnClass = new ReturnClassImpl(context.generics()
                                                               .get(2)); // Class R for right method searching
    final Set<Method> methods = new ByParameterClass(masterClass,
                                                     guestClass,
                                                     this.methodName,
                                                     returnClass,
                                                     new ByParameterInterfaces(masterClass,
                                                                               guestClass,
                                                                               this.methodName,
                                                                               returnClass,
                                                                               new ByParameterSuperClass(masterClass,
                                                                                                         guestClass,
                                                                                                         this.methodName,
                                                                                                         returnClass,
                                                                                                         new EmptyMethods()))).findMethods();
    final ResultFunction<M, G, R> method;
    if (methods.isEmpty()) {
      method = new ResultFunction<M, G, R>() {
        @Override
        public String toString() {
          return "default method";
        }

        @Override
        public R apply(final M master,
                       final G guest) {
          return Dispatch.this.defaultMethod.apply(master,
                                                   guest);
        }
      };
    } else {
      final Iterator<Method> iterator = methods.iterator();
      MethodRepresentation currentMethod = new OneMethod(iterator.next());
      while (iterator.hasNext()) {
        final MethodRepresentation nextMethod = new OneMethod(iterator.next());
        currentMethod = new OneOfTwoMethods(currentMethod,
                                            nextMethod);
      }
      final Method finalMethod = currentMethod.toMethod();
      method = new MethodFunction<>(finalMethod);
    }
    return method;
  }
}
