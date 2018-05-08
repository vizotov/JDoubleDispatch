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
import java.util.Set;

/**
 * Searching method by the interface of parameter
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 */
class ByParameterInterfaces
    implements MethodsSource {
  private final Class<?> guestClass;
  private final Class<?> returnClass;
  private final Class<?> masterClass;
  private final String methodName;
  private final MethodsSource nextMethodsSource;

  ByParameterInterfaces(
      final Class<?> masterClass,
      final Class<?> guestClass,
      final String methodName,
      final Class<?> returnClass,
      final MethodsSource nextMethodsSource) {
    this.guestClass = guestClass;
    this.returnClass = returnClass;
    this.masterClass = masterClass;
    this.methodName = methodName;
    this.nextMethodsSource = nextMethodsSource;
  }

  /**
   * use this function for performance reasons, instead of creating the one time used object
   */
  private static Set<Method> findMethod(
      final Class<?> guestClass,
      final Class<?> masterClass,
      final String methodName,
      final Class<?> returnClass,
      final MethodsSource nextMethodsSource) {
    final Set<Method> methods = nextMethodsSource.findMethods();
    final MethodsSource emptyMethods = new EmptyMethods();
    for (final Class<?> guestInterface : guestClass.getInterfaces()) {
      try {
        final Method method = masterClass.getMethod(methodName, guestInterface);
        if (returnClass.isAssignableFrom(method.getReturnType())) {
          methods.add(method);
        }
      } catch (final NoSuchMethodException ignored) {
      }
      methods.addAll(ByParameterInterfaces.findMethod(masterClass,
                                                      guestInterface,
                                                      methodName,
                                                      returnClass,
                                                      emptyMethods));
    }
    return methods;
  }

  @Override public final Set<Method> findMethods() {
    return ByParameterInterfaces.findMethod(this.guestClass,
                                            this.masterClass,
                                            this.methodName,
                                            this.returnClass,
                                            this.nextMethodsSource);
  }
}
