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

import java.util.Objects;

/**
 * the master type of method
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
class MasterOf
    implements TypeRepresentation {
  private final MethodRepresentation methodRepresentation;

  MasterOf(final MethodRepresentation methodRepresentation) {
    this.methodRepresentation = methodRepresentation;
  }

//  @Override public final boolean isSubtypeOf(final TypeRepresentation typeRepresentation)
//      throws MethodAmbiguouslyDefinedException {
//    return new MasterOfMethod(this.methodRepresentation.toMethod()).isSubtypeOf(typeRepresentation);
//  }

  @Override
  public final Class<?> toClass() throws
                                  MethodAmbiguouslyDefinedException {
    return new MasterOfMethod(this.methodRepresentation.toMethod()).toClass();
  }

  @Override public final int hashCode() {
    return Objects.hash(this.methodRepresentation);
  }

  @SuppressWarnings("MethodWithMultipleReturnPoints") @Override
  public final boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || !this.getClass()
                            .equals(obj.getClass())) {
      return false;
    }
    final MasterOf guestOf = (MasterOf) obj;
    return Objects.equals(this.methodRepresentation, guestOf.methodRepresentation);
  }
}
