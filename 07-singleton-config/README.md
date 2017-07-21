# 07 &mdash; Singleton Config
> creating a singleton to hold the config a facilitate access to clients

## Description
The example illustrates how to create a singleton that holds the config instance. The pattern used is the eagerly loaded singleton for simplicity. The singleton is bound to an UUID so that you can check whether more than expected configs are created.