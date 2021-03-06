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

import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import su.izotov.java.ddispatch.guest.impl.InheritedParamImpl;
import su.izotov.java.ddispatch.guest.impl.MultiMethodParameterGuest;
import su.izotov.java.ddispatch.master.Master;

/**
 * if more than one method found
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public class MultiMethodTest {
  private static RestrictionInterface defaultMethod(
      final RestrictionInterface master, final RestrictionInterface guest) {
    return new ExpectedResult("defaultMethod");
  }

  @Test(expected = MethodAmbiguouslyDefinedException.class)
  public final void testTwoDifferentMethod() throws
                                             MethodAmbiguouslyDefinedException {
    new ExampleDispatch(new Master(),
                        new MultiMethodParameterGuest(),
                        MultiMethodTest::defaultMethod).invoke();
    assertTrue(true);
  }

  @Test public final void testTwoInheritedMethod() throws
                                                   MethodAmbiguouslyDefinedException {
    Assert.assertEquals(new ExpectedResult("Master inherited method"),
                        new ExampleDispatch(new Master(),
                                            new InheritedParamImpl(),
                                            MultiMethodTest::defaultMethod).invoke());
  }
}
