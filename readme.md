# watson

A library for creating typed service contracts for Play in Scala. This is very much
a work in progress.

Some notes and thoughts below.

## Problem
HTTP web services implicitly have contracts. However, defining them is usually done
through documentation which is separate from the code. The service then has to hack
together a bunch of rules to validate that a request conforms to the contract and
then has to form some sort of response back to the user that makes sense. Its the
latter part here that is usually messy, inconsistent from docs, and kind of a pain
to have to do in any web service.

## Goal
Create some sort of type tree or AST that can represent all of the required components.
Something to the effect of:

```scala
val s = Service(
  method       = PUT
  querystring  = RequiredParam(id,   type = Int   ) ::
                 OptionalParam(code, type = String) ::
                 OptionalRequiredParamGroup(extras)(
                     Param(extended_features, type = NoType)
                     Param(feature_id,        type = Int   )
                 )
  body         = Body(type = MyServicePut)
  responseBody = Body(type = MyService)
)

s.handle { request =>
  request.qs.id             // Int
  request.qs.code           // Option[String]
  request.qs.extras         // Option[{extendedFeatures: True, featureId: Int}]
  request.body              // MyServicePut

  process()                 // return type of MyService
}
```

From the types above we can deduce the following:

- `PUT` request
- required `id` and optional `code` querystring
- optional group of params, all of which must either be present or absent

## Implementation Thoughts

- The `handle` function’s `request` param
    - Will need to be some generic type such as `Request`
    - In order to have typed values for qs (e.g. `request.qs.id`), we will need specialization of `Request`
    - These specializations can be generated via a macro, ideally at the time the service object is created
    - `apply` will need to be a macro
- The best we can really do is compile time safety with runtime checks...
    - Is this true?
    - Goes back to typed network protocols (Idris papers)
- `s` as an object
    - we create a `Service` and assign it to a val `s`
    - What if the result of the macro actually returns an object (is this possible?)
    - We can put implicit converters in the object for `MyService => ResultContext`
    - `ResultContext` can be used to return body data as well as headers and such
- People need to format the error responses for their standards. Some way to plug in formatters would be super awesome
