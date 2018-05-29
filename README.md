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
If the method is found, it is started. If not found, the specified default method is started.

If several suitable methods are found, then one method is selected in accordance with the following algorithm:

If the parameters of the two methods belong to the same class hierarchy, then a method with a 
parameter belonging to the child class or interface is selected. If the parameters belong to 
different independent hierarchies, then it throws a MethodAmbiguouslyDefinedException.

After this procedure, only methods with parameters of the same type remain in the list.

Further, if declaring classes of both methods belong to the same hierarchy, then one method 
overrides the other. Therefore, the override method is selected.

If the declaring classes of the two methods refer to different, unrelated hierarchies, then it 
throws a MethodAmbiguouslyDefinedException.

### Setup

Releases are published to sonatype.org and to maven central. You may download artefacts manually:

[Sonatype](https://oss.sonatype.org/content/groups/staging/su/izotov/JDoubleDispatch/)

[Maven Central](http://repo1.maven.org/maven2/su/izotov/JDoubleDispatch/)

or using build systems.

Maven:

```xml
<dependency>
  <groupId>su.izotov</groupId>
  <artifactId>JDoubleDispatch</artifactId>
  <version>0.3</version>
</dependency>
```

Gradle:

```groovy
dependencies {
    compile 'su.izotov:JDoubleDispatch:0.3'
}
```