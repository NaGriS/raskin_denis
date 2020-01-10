using System;
using AutoDiff;

namespace AutomaticDiff
{
    static class Autodiffer
    {
        public static void EvaluateSimpleExponentFunction(double xVal, double yVal)
        {
            // we will use a function of two variables
            Variable x = new Variable();
            Variable y = new Variable();

            // func(x, y) = (x + y) * exp(x - y)
            Term func = (x + y) * TermBuilder.Exp(x - y);

            // define the ordering of variables, and a point where the function will be evaluated/differentiated
            Variable[] vars = { x, y };
            double[] point = { xVal, yVal };

            double eval = func.Evaluate(vars, point);
            double[] gradient = func.Differentiate(vars, point);

            // write the results
            Console.WriteLine("Exponent function:");
            Console.WriteLine("f({0}, {1}) = {2}", xVal, yVal, eval);
            Console.WriteLine("Gradient of f at ({0}, {1}) = ({2}, {3})", xVal, yVal, gradient[0], gradient[1]);
        }

        public static void EvaluateSimplePowFunction(double xVal, double yVal)
        {
            // we will use a function of two variables
            Variable x = new Variable();
            Variable y = new Variable();

            // func(x, y) = x^y - (x+y)^2
            var func = TermBuilder.Power(x, y) - TermBuilder.Power(x + y, 2);

            // define the ordering of variables, and a point where the function will be evaluated/differentiated
            Variable[] vars = { x, y };
            double[] point = { xVal, yVal };

            double eval = func.Evaluate(vars, point);
            double[] gradient = func.Differentiate(vars, point);

            // write the results
            Console.WriteLine("Power function:");
            Console.WriteLine("f({0}, {1}) = {2}", xVal, yVal, eval);
            Console.WriteLine("Gradient of f at ({0}, {1}) = ({2}, {3})", xVal, yVal, gradient[0], gradient[1]);
        }

        public static void EvaluateSimpleExponentFunctionMultipleTimes(int numberOfPoints)
        {
            // we will use a function of two variables
            Variable x = new Variable();
            Variable y = new Variable();

            // func(x, y) = (x + y) * exp(x - y)
            Term func = (x + y) * TermBuilder.Exp(x - y);

            // create compiled term and use it for many gradient/value evaluations
            ICompiledTerm compiledFunc = func.Compile(x, y);

            // we can now efficiently compute the gradient of "func" many times with different inputs.
            // compilation helps when we need many evaluations of the same function.
            Console.WriteLine("\r\nEvaluation started");
            for (int i = 0; i < numberOfPoints; ++i)
            {
                var xVal = i / 64.0;
                var yVal = 1 + i / 128.0;

                var diffResult = compiledFunc.Differentiate(xVal, yVal);
                var gradient = diffResult.Item1;
                var value = diffResult.Item2;
                Console.WriteLine("The value of func at x = {0}, y = {1} is {2} and the gradient is ({3}, {4})",
                    xVal, yVal, value, gradient[0], gradient[1]);
            }
        }
    }
}
