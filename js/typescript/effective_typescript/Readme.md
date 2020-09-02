# [Effective TypeScript](https://learning.oreilly.com/library/view/effective-typescript/9781492053736/)

- [Effective TypeScript](#effective-typescript)
  - [Chapter 1. Getting to Know TypeScript](#chapter-1-getting-to-know-typescript)
    - [JS Runtime](#js-runtime)
    - [Type erasure](#type-erasure)
    - [tsConfig](#tsconfig)

## Chapter 1. Getting to Know TypeScript

### JS Runtime
* Code generation is independent of the type system
* Even when there are type errors TypeScript will still parse your code and emit JavaScript
  * Can be prevented with a tsconfig option `noEmitOnError`
* The type checker is static - cannot always spot code that will throw exceptions, but it will try
  * E.g. unbound array access
* TypeScript’s type system models the runtime behavior of JavaScript
  * This may result in some surprises
  ```
  const x = 2 + '3';  // OK, type is string
  const y = '2' + 3;  // OK, type is string
  ```
  * The type checker flags issues in all of these statements, even though they do not throw exceptions at runtime
* Structural Typing
  * JavaScript is inherently duck typed
  * TypeScript models this behavior too
    * Structurally compatible types can be used interchangeably transparently without any declaration

### Type erasure
* You Cannot Check TypeScript Types at Runtime
  * `instanceof` can't be used with typescript types
    * workarounds
      * check for the presence of a property
        * To ascertain the type you’re dealing with
      * use tags, tagged unions to explicitly store the type in a way that’s available at runtime
      * Converting `type` to `class`
        * `class` info is retained at runtime (as it is a JS feature)
          * `instanceof` can be used
  * `as <type>` is a type assertion that is not available at runtime for type conversion/cast
    * `typeof()` along with explicit type casts need to used
  * TypeScript types are “erasable”
    * part of compilation to JavaScript is simply removing all the `interfaces`, `types`, and type annotations from your code
* Runtime Types May Not Be the Same as Declared Types
  *  In JavaScript code, a user might call with a different type than the declared Typescript type
* You Cannot Overload a Function Based on TypeScript Types
  * Due to type erasure function overloading with types is not possible
  * You can provide multiple declarations for a function, but only a single implementation
    * the declarations provide only type information
* TypeScript Types Have No Effect on Runtime Performance
  * While there is no runtime overhead, the TypeScript compiler will introduce build time overhead

### tsConfig
* `noImplicitAny` controls whether variables must have known types
  * TypeScript is the most helpful when it has type information, so you should be sure to set `noImplicitAny` whenever possible. 
  * For new projects, you should start with `noImplicitAny` on
* `strictNullChecks` controls whether `null` and `undefined` are permissible values in every type
  * tremendously helpful for catching errors involving `null` and `undefined` values
    * could help prevent “undefined is not an object” runtime error
  * but it does increase the difficulty of using the language
* turn on `strict` to enable all similar checks 
  * TypeScript is able to catch the most errors with `strict`, so this is where you eventually want to wind up
* `noEmitOnError` to disable output on errors
  * You should aim for zero errors when you commit code, lest you fall into the trap of having to remember what is an expected or unexpected error
* 