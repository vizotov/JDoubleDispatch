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
package su.izotov.java.ddispatch.types;

import su.izotov.java.ddispatch.methods.MethodAmbiguouslyDefinedException;

/**
 * the type representation
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public interface TypeRepresentation {
  /**
   * the type is subtype of parameter type
   * @param typeRepresentation type
   * @return boolean
   * @throws MethodAmbiguouslyDefinedException exception
   */
  default boolean isSubtypeOf(final TypeRepresentation typeRepresentation)
      throws MethodAmbiguouslyDefinedException {
    return typeRepresentation.isSupertypeOf(this.toClass());
  }

  default boolean isSupertypeOf(final Class<?> type)
      throws MethodAmbiguouslyDefinedException {
    return !this.toClass().equals(type) && this.toClass().isAssignableFrom(type);
  }

  /**
   * represent as Class object
   * @return clazz
   * @throws MethodAmbiguouslyDefinedException exception
   */
  Class<?> toClass()
      throws MethodAmbiguouslyDefinedException;
}
