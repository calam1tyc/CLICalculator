# CLICalculator
Text-based calculator written in Java which can evaluate complex arithmetic expressions.

This program supports five different operators, in order of precedence:

'^' (exponent)

'*' (multiply)

'/' (divide)

'+' (add)

'-' (subtract)

Negative and decimal numbers are supported.

There is also support for functions with the syntax func_name(expression).

The default functions are:

sqrt() (square root)

log_10() (log base 10)

logn() (natural log)

sin() (sine)

cos() (cosine)

tan() (tangent)
  
Stand-alone parentheses or those part of functions are evaluated first.

Ex. (3 + 4) * 2 + sqrt(4 + 5) returns 17.0
 
Implementing new functions or binary operators can be done without deeply analyzing the code.
 
