# 01 &mdash; Simple Config Wrapper
> creating a simple wrapper on top of the Config

## Description
According to the documentation, libraries should use a `Config` instance provided by the app. This example illustrates how to create that simple wrapper.

By doing so, application code will not directly depend on the `Config` library, which results in a more loosely-coupled approach to configuration.

The example also illustrates how you can override a value using *Java properties*.
