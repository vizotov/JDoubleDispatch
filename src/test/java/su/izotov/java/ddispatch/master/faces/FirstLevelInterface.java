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
package su.izotov.java.ddispatch.master.faces;

import su.izotov.java.ddispatch.ExpectedResult;
import su.izotov.java.ddispatch.RestrictionInterface;
import su.izotov.java.ddispatch.guest.faces.InInterfaceGuest;
import su.izotov.java.ddispatch.guest.faces.InheritedParamInterface;
import su.izotov.java.ddispatch.guest.faces.MultiMethodParamInterface;
import su.izotov.java.ddispatch.guest.faces.SecondMultiMethodParamInterface;

/**
 * one of interfaces, implemented by Master class
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 */
public interface FirstLevelInterface
    extends SecondLevelInterface {
  @Override default RestrictionInterface example(final InInterfaceGuest iif) {
    return new ExpectedResult("InInterface method");
  }

  default RestrictionInterface example(final MultiMethodParamInterface multi) {
    return new ExpectedResult("MultiMethodParamInterface second method");
  }

  default RestrictionInterface example(final SecondMultiMethodParamInterface multi) {
    return new ExpectedResult("SecondMultiMethodParamInterface second method");
  }

  default RestrictionInterface example(final InheritedParamInterface direct) {
    return new ExpectedResult("FirstLevelInterface method");
  }
}
