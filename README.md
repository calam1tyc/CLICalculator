# CLICalculator
Text-based calculator written in Java which can evaluate complex arithmetic expressions.

This program supports five different operators, in order of precedence:

  * '^' (exponent)

  * '*' (multiply)

  * '/' (divide)

  * '+' (add)

  * '-' (subtract)

Negative and decimal numbers are supported.

---

There is also support for functions with the syntax func_name(expression).

The default functions are:

  * sqrt() (square root)

  * log_10() (log base 10)

  * logn() (natural log)

  * sin() (sine)

  * cos() (cosine)

  * tan() (tangent)

Custom functions can be implemented by adding them to the functionResult method.

---
  
Parentheses are always evaluated before the rest of the expression and can be nested.

Ex. (3 * (4 - 3)) * 2 + sqrt(4 + 5) returns 9.0

 
