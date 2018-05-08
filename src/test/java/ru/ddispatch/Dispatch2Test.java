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

import java.lang.reflect.InvocationTargetException;
import org.junit.Assert;
import org.junit.Test;
import ru.ddispatch.guest.impl.OuterImpl;
import ru.ddispatch.guest.impl.Param;
import ru.ddispatch.guest.impl.Param2;
import ru.ddispatch.master.Master;

/**
 * tests
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 */
public class Dispatch2Test {
  private static RestrictionInterface defaultMethod(
      final RestrictionInterface master, final RestrictionInterface guest) {
    return new ExpectedResult("defaultMethod");
  }

  @Test public final void testParamByInterface()
      throws InvocationTargetException, IllegalAccessException {
    Assert.assertEquals(new ExpectedResult("SuperParam method"),
                        new ExampleDispatch(new Master(), new Param(), Dispatch2Test::defaultMethod)
                            .invoke());
  }

  @Test public final void testParamBySuperInterface()
      throws InvocationTargetException, IllegalAccessException {
    Assert.assertEquals(new ExpectedResult("SecondLevelParamInterface method"),
                        new ExampleDispatch(new Master(),
                                            new Param2(),
                                            Dispatch2Test::defaultMethod).invoke());
  }

  @Test public final void testNoMethods()
      throws InvocationTargetException, IllegalAccessException {
    Assert.assertEquals(new ExpectedResult("defaultMethod"),
                        new ExampleDispatch(new Master(),
                                            new OuterImpl(),
                                            Dispatch2Test::defaultMethod).invoke());
  }
}
