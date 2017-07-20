# 04 &mdash; Customizing Waterfall Config
> merging configuration objects to customize configuration precedence

## Description
Any two `Config` objects can be merged with an operation called `withFallback`.

In the example, it is demonstrated how to implement the following waterfall configuration, where a config property defined on a top config source has precedence over one with the same name but defined in a config source below.

