# 02 &mdash; Config Waterfall
> creating a simple wrapper on top of the Config

## Description
The *Config* library establishes the following precedence for configuration sources:
+ Java System Properties
+ `application.conf`
+ `application.properties`
+ `reference.conf`

The *config* library features an overloaded `ConfigFactory.load(<app-basename>)` to load a custom `<app-basename>.conf` application configuration file.

The *config* library also supports using the syntax ${foo.bar} to reference existing vars.

In the example we validate how this waterfall approach works, and also illustrate how you can use the syntax:
```
computed-value = ${common.value}
```

Note that in the example we load a `custom.conf` instead of the `application.conf`, but that the method used does not allow to use a path to the file as in `folder/config-file`.