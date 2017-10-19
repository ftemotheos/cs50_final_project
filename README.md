# The calculator of arithmetic expressions!

The program is a console application written in the Java programming language. 
Insensitive to the case of characters, and also ignores the presence of any number of spaces in the expression. 
On startup, it accepts a string as the parameter of the main() method, and if it does not, it asks for keyboard input in the console window. 
Allows you to enter integer and real numbers in decimal format, as well as named constant literal E or PI instead of directly entering their values.

Supports the following arithmetic operations in descending order of priority:
1.	The operation to increase the priority of evaluating the expression, the operation of evaluating the absolute value of an expression, and an arbitrary function from the following under this list. 
2.	Operation unary plus and unary minus.
3.	Multiplication, division, and remainder of division for two operands.
4.	Exponentiation for two operands.
5.	Addition and subtraction for two operands.

Available arithmetic functions are as follows:

-  With only one argument:

round(x)	-	rounding x to the nearest whole, and in case of equidistance, then to the 
 			      nearest larger integer;
            
sqrt(x)		-	calculation of the square root of the number x;

cbrt(x)		-	calculation of the cubic root of the number x;

sin(x)		-	calculation of the sine of the number x, the number is given in radians;

cos(x)		-	calculating the cosine of the number x, the number is given in radians;

tg(x)		-	calculation of the tangent number x, the number is given in radians.

-  With strictly two arguments:

log(x,y)		-	calculating the logarithm of the number x by the base y.

-  With an arbitrary number of arguments:

min(x1,x2,…,xn)	-	returns the minimum of all argument values;

max(x1,x2,…,xn)	-	returns the maximum of all argument values.

Object-oriented approach when writing a program allows you to easily add new operations and functions. To do this, simply write new classes.

The logic of the program is as follows:

First, the resulting string with an arithmetic expression is parsed into tokens using the parser I wrote. 
Returnable tokens are objects and are characterized by type and content. 
A type can be one of the following: the beginning of the expression, the operand, the operation, the function, and the end of the expression. 
The content is a string field, and for a token function, also a list-field of strings representing the parameters of the arithmetic function. 
To calculate the initial expression, you need to break it into subexpressions and calculate their result. 
The results obtained are used to calculate the original expression. 
This algorithm is sometimes represented as an abstract syntax tree in which the root contains the original expression, other nodes are nonterminal expressions, and leaves are terminal expressions. 
By nonterminal expressions we mean expressions that can be divided into components, and under terminal ones, operands, i.e., numbers. 
My program uses the principle of a syntax tree, implementing it through a stack of method calls. 
The corresponding methods are called one of the other in order of increasing priority of the computed operations, and return the calculated values of the subexpressions in decreasing order. 
When calculating operations and functions, the Interpreter OOP pattern design was used. Operands are interpreted as real numbers with double precision of Java language, operations as Java arithmetic operations admissible in Java, functions are realized through mathematical functions of Java language. 
The results of the calculations are stored in a context object, which is a wrapper object over a hash table. 
Thus, all calculated subexpressions are available in memory to evaluate expressions containing them. 
If a user enters a string which contents cannot be split into tokens, the program displays a message about the lexical error. 
If the order of the tokens does not match the rules for writing arithmetic expressions, the program displays a syntax error message. 
If an exception is thrown when converting a string to a real number with double precision or when working with a hash table, the program will display a message about the semantic error.

https://www.youtube.com/watch?v=oBh-xoz0n4M
