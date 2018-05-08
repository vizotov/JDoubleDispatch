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
package ru.ddispatch;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Searching method by the superclass of parameter
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 */
public class ByParameterSuperClass
    implements MethodsSource {
  private final Class<?>      guestClass;
  private final Class<?>      returnClassRestriction;
  private final Class<?>      masterClass;
  private final String        methodName;
  private final MethodsSource nextMethodsSource;

  ByParameterSuperClass(
      final Class<?> masterClass,
      final Class<?> guestClass,
      final String methodName,
      final Class<?> returnClass,
      final MethodsSource nextMethodsSource) {
    this.guestClass = guestClass;
    this.returnClassRestriction = returnClass;
    this.masterClass = masterClass;
    this.methodName = methodName;
    this.nextMethodsSource = nextMethodsSource;
  }

  @Override public final Set<Method> findMethods() {
    final Set<Method> methods = this.nextMethodsSource.findMethods();
    if (this.guestClass.getSuperclass() != null) {
      methods.addAll(ByParameterClass.findMethods(
          this.masterClass,
          this.guestClass.getSuperclass(),
          this.methodName,
          this.returnClassRestriction,
          new ByParameterInterfaces(
              this.masterClass,
              this.guestClass.getSuperclass(),
              this.methodName,
              this.returnClassRestriction,
              new EmptyMethods())));
    }
    return methods;
  }
}
