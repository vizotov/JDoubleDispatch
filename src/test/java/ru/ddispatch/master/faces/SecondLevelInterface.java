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
package ru.ddispatch.master.faces;

import ru.ddispatch.ExpectedResult;
import ru.ddispatch.RestrictionInterface;
import ru.ddispatch.guest.faces.SecondLevelParamInterface;
import ru.ddispatch.guest.faces.SuperParam;
import ru.ddispatch.guest.faces.InInterfaceGuest;
import ru.ddispatch.guest.faces.InSecondInterfaceParameter;

/**
 * one of interfaces, implemented by Master class
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 */
public interface SecondLevelInterface {
  default RestrictionInterface example(final InInterfaceGuest iif) {
    return new ExpectedResult("it never happens");
  }

  default RestrictionInterface example(final InSecondInterfaceParameter iif) {
    return new ExpectedResult("InSecondInterface method");
  }

  default RestrictionInterface example(final SuperParam superParam) {
    return new ExpectedResult("SuperParam method");
  }

  default RestrictionInterface example(final SecondLevelParamInterface sLevelParamInterface) {
    return new ExpectedResult("SecondLevelParamInterface method");
  }
}