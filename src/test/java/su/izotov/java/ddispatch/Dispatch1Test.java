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
import org.junit.Assert;
import org.junit.Test;
import su.izotov.java.ddispatch.guest.impl.DirectParameterGuest;
import su.izotov.java.ddispatch.guest.impl.InInterfaceParameterGuest;
import su.izotov.java.ddispatch.guest.impl.InSecondInterfaceParameterGuest;
import su.izotov.java.ddispatch.guest.impl.SuperDirectParameterGuest;
import su.izotov.java.ddispatch.master.Master;
import su.izotov.java.ddispatch.methods.MethodAmbiguouslyDefinedException;

/**
 * tests
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 */
public class Dispatch1Test {
  private static RestrictionInterface defaultMethod(
      final RestrictionInterface master, final RestrictionInterface guest) {
    return new ExpectedResult("defaultMethod");
  }

  @Test public final void testDirectParameter()
      throws InvocationTargetException, IllegalAccessException, MethodAmbiguouslyDefinedException {
    Assert.assertEquals(new ExpectedResult("Direct method"),
                        new ExampleDispatch(new Master(),
                                            new DirectParameterGuest(),
                                            Dispatch1Test::defaultMethod).invoke());
  }

  @Test public final void testSuperDirectParameter()
      throws InvocationTargetException, IllegalAccessException, MethodAmbiguouslyDefinedException {
    Assert.assertEquals(new ExpectedResult("SuperDirect method"),
                        new ExampleDispatch(new Master(),
                                            new SuperDirectParameterGuest(),
                                            Dispatch1Test::defaultMethod).invoke());
  }

  @Test public final void testInInterfaceParameter()
      throws InvocationTargetException, IllegalAccessException, MethodAmbiguouslyDefinedException {
    Assert.assertEquals(new ExpectedResult("InInterface method"),
                        new ExampleDispatch(new Master(),
                                            new InInterfaceParameterGuest(),
                                            Dispatch1Test::defaultMethod).invoke());
  }

  @Test public final void testInSecondInterfaceParameter()
      throws InvocationTargetException, IllegalAccessException, MethodAmbiguouslyDefinedException {
    Assert.assertEquals(new ExpectedResult("InSecondInterface method"),
                        new ExampleDispatch(new Master(),
                                            new InSecondInterfaceParameterGuest(),
                                            Dispatch1Test::defaultMethod).invoke());
  }
}
