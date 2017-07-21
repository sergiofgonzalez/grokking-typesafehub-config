# Grokking typesafehub config library
> Examples and insights on typesafehub `Config` library

# Description
**Config** is a configuration library for JVM languages. It is implementated in plain Java with no additional dependencies and supports files in Java properties, JSON and a *human-friendly JSON superset*.

This repository explores its capabilities and extensibility points in different examples, from the simplest ones to the more complex.

Also, this document will collect written details that I've found difficult to grasp by reading the official docs on https://github.com/typesafehub/config.




## Things I need to cover
- [X] Run the examples from the official docs
- [X] Learn how to specify the configuration in several files (common, classes)
- [X] Learn how to implement a waterfall approach (external file >> environment vars >> Java Properties >> file in jar)
- [X] Learn how to switch that JSON superset for YAML 
- [X] How to support profiles
- [X] How to use different filenames than provided
- [X] Explore what the doc says: A config can be created with the parser methods in ConfigFactory or built up from any file format or data source you like with the methods in `ConfigValueFactory`.
- [X] Use static functions rather than `new` (FactoryIdiom)
- [ ] Create a class that accepts a parameter or environment variable to load profile or section of the config
- [X] Explore configFactory.systemEnvironment()
- [ ] Parsing and transforming between environment variables and config params (as nconf does)
- [ ] Encryption?
- [X] license


## Examples

### [00 &mdash; Hello typesafehub Config!](./00-hello-typesafehub-config/)
Illustrates how to read the configuration properties using the default mechanism of the *Config* library

### [01 &mdash; Simple Config Wrapper](./01-simple-config-wrapper/)
Illustrates how to read the configuration properties using the default mechanism of the *Config* library

### [02 &mdash; Waterfall Configuration](./02-config-waterfall/)
Illustrates how to read the configuration properties using the default mechanism of the *Config* library

### [03 &mdash; Customizing Config Filename](./03-customizing-config-filename/)
Illustrates how to customize the location and name of the file that contains the application configuration

### [04 &mdash; Customizing Waterfall Config](./04-customizing-waterfall-config/)
Illustrates how to merge config trees to implement a waterfall configuration in which a precedence is established between different configuration sources.

### [05 &mdash; Hello YAML Config](./05-hello-yaml-config/)
Illustrates how to create a `Config` object from a YAML file.

### [06 &mdash; Spring-like Profiles](./06-spring-like-profiles/)
Illustrates how to restrict the loading of configuration properties only to a certain node of the whole configuration properties, emulating the same behavior as Spring does with profiles.

### [07 &mdash; Singleton Config](./07-singleton-config/)
Illustrates how to create a singleton (eagerly-loaded singleton) to hold the configuration.