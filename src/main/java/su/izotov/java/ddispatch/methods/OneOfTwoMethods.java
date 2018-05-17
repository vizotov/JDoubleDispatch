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
package su.izotov.java.ddispatch.methods;

import java.lang.reflect.Method;

/**
 * Select firstMethod method of secondMethod in accordance with the following algorithm:
 * <p>If the parameters of the secondMethod methods belong to the same class hierarchy, then a method with a
 * parameter belonging to the child class or interface is selected. If the parameters belong to
 * different independent hierarchies, then it throws a MethodAmbiguouslyDefinedException.</p>
 * <p>After this procedure, only methods with parameters of the same type remain in the list.</p>
 * <p>Further, if declaring classes of both methods belong to the same hierarchy, then firstMethod method
 * overrides the other. Therefore, the override method is selected.</p>
 * <p>If the declaring classes of the secondMethod methods refer to different, unrelated hierarchies, then
 * it throws a MethodAmbiguouslyDefinedException.</p>
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public class OneOfTwoMethods
    implements MethodRepresentation {
  private final MethodRepresentation firstMethod;
  private final MethodRepresentation secondMethod;

  public OneOfTwoMethods(
      final MethodRepresentation firstMethod, final MethodRepresentation secondMethod) {
    this.firstMethod = firstMethod;
    this.secondMethod = secondMethod;
  }

  @Override public final Method toMethod()
      throws MethodAmbiguouslyDefinedException {
    final TypeRepresentation guestOfOne = new GuestOf(this.firstMethod);
    final TypeRepresentation guestOfTwo = new GuestOf(this.secondMethod);
    final TypeRepresentation masterOfOne = new MasterOf(this.firstMethod);
    final TypeRepresentation masterOfTwo = new MasterOf(this.secondMethod);
    final Method ret;
    if (guestOfOne.isSubtypeOf(guestOfTwo)) {
      ret = this.firstMethod.toMethod();
    } else if (guestOfTwo.isSubtypeOf(guestOfOne)) {
      ret = this.secondMethod.toMethod();
    } else if (guestOfOne.equals(guestOfTwo)) {
      if (masterOfOne.isSubtypeOf(masterOfTwo)) {
        ret = this.firstMethod.toMethod();
      } else if (masterOfTwo.isSubtypeOf(masterOfOne)) {
        ret = this.secondMethod.toMethod();
      } else {
        ret = this.firstMethod.toMethod(); // never happens
      }
    } else {
      throw new MethodAmbiguouslyDefinedException(this.firstMethod.toMethod(),
                                                  this.secondMethod.toMethod());
    }
    return ret;
  }
}
