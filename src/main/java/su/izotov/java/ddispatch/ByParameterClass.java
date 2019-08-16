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
 * Searching method by the class of parameter
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 */
class ByParameterClass
    implements MethodsSource {
  private final GuestClass      guestClass;
  private final ReturnClass      returnClassRestriction;
  private final MasterClass      masterClass;
  private final String        methodName;
  private final MethodsSource nextMethodsSource;

  ByParameterClass(
      final MasterClass masterClass,
      final GuestClass guestClass,
      final String methodName,
      final ReturnClass returnClass,
      final MethodsSource nextMethodsSource) {
    this.guestClass = guestClass;
    this.returnClassRestriction = returnClass;
    this.masterClass = masterClass;
    this.methodName = methodName;
    this.nextMethodsSource = nextMethodsSource;
  }

  @Override public final Set<Method> findMethods() {
    return ByParameterClass.findMethods(
        this.masterClass,
        this.guestClass,
        this.methodName,
        this.returnClassRestriction,
        this.nextMethodsSource);
  }

  /**
   * use this function for performance reasons, instead of creating the one time used object
   */
  public static Set<Method> findMethods(
      final MasterClass masterClass,
      final GuestClass guestClass,
      final String methodName,
      final ReturnClass returnClass,
      final MethodsSource nextMethodsSource) {
    final Set<Method> methods = nextMethodsSource.findMethods();
    try {
      final Method method = masterClass.toClass().getMethod(methodName, guestClass.toClass());
      if (returnClass.toClass().isAssignableFrom(method.getReturnType())) {
        methods.add(method);
      }
    } catch (final NoSuchMethodException ignored) {
    }
    return methods;
  }
}
