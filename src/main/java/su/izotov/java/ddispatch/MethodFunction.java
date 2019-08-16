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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public class MethodFunction<M, G, R>
    implements ResultFunction<M, G, R> {

  private final Method method;

  public MethodFunction(final Method method) {
    this.method = method;
  }

  @Override
  public final String toString() {
    return this.method.getReturnType()
                      .getSimpleName() + ' ' + this.method.getDeclaringClass()
                                                          .getSimpleName() + '.' + this.method.getName() + '(' + this.method.getParameterTypes()[0].getSimpleName() + ')';
  }

  @Override
  public final R apply(final M master,
                       final G guest) {
    try {
      //noinspection unchecked
      return (R) this.method.invoke(master,
                                    guest);
    } catch (final IllegalAccessException | InvocationTargetException ex) {
      throw new IllegalStateException(ex);
    }
  }
}
