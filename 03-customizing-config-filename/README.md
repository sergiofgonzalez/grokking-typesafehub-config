# 03 &mdash; Customizing Config Filename
> Customizing the location and name of the application config file

## Description
The example illustrates how to customize the location and name of the application config file using 
```java
Config config = ConfigFactory.load(ConfigFactory
                            .parseResources("config/app.conf"));
```