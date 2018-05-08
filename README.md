# JDoubleDispatch

If you need to use such constructions in your java-code:

```java
class Master {
Object someMethod(Object param) {
  Object ret;
  if (param instanceof Class1) {
    ret = someMethod((Class1)param);
  } else if (param instanceof Class2) {
    ret = someMethod((Class2)param);
  } else if (param instanceof Class3) {
    ret = someMethod((Class3)param);
  } else {
    ret = defaultMethod(param);
  }
  return ret;
}

Object someMethod(Class1 param) {
  ...
}

Object someMethod(Class2 param) {
  ...
}

Object someMethod(Class3 param) {
  ...
}

Object defaultMethod(Object param) {
  ...
}
}
```
that is, to make decisions based on the runtime type of the object, perhaps [double dispatch](https://en.wikipedia.org/wiki/Double_dispatch) feature will help you.

To use this functionality, you need to implement the following:

First, create a class containing some parameters.

```java
// We want to realize double dispatch version of 'someMethod' method
class SomeMethodDispatch
    extends Dispatch<Master //Type restriction of master object
    , Guest // Type restriction of parameter object
    , Returned // Type restriction of returned object. All methods, which return result is
               // not assignable to this type, will be ignored
    > {
  SomeMethodDispatch(
      final Master master,
      final Guest guest,
      final BiFunction<Master, Guest, Returned> defaultMethod) {
    super(master, guest, "someMethod", defaultMethod);
  }
}
```

After that, the first method listed in the beginning of the class Master will look like this:

```java
...
Object someMethod(Object param) {
  return new SomeMethodDispatch(this,param,this::defaultMethod).invoke();
}
...
```

When this method is called, the actual method will be searched based on runtime object classes. 
Method can be found both in the Master class itself, and in its superclasses and implemented interfaces.
If the method is found, it is started. If not found, the specified method is started by default.