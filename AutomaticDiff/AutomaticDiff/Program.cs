using System;

namespace AutomaticDiff
{
    class Program
    {
        static void Main(string[] args)
        {
            double xVal = 3;
            double yVal = 1;
            // func(x, y) = (x + y) * exp(x - y)
            Autodiffer.EvaluateSimpleExponentFunction(xVal, yVal);

            xVal = 0.5;
            yVal = 4;
            // func(x, y) = (x + y) * exp(x - y)
            Autodiffer.EvaluateSimpleExponentFunction(xVal, yVal);

            // func(x, y) = x^y - (x+y)^2
            xVal = 0.5;
            yVal = 4;
            Autodiffer.EvaluateSimplePowFunction(xVal, yVal);

            var numberOfPoints = 64;
            // func(x, y) = (x + y) * exp(x - y), where x = 0 to numberOfPoints / 64.0 and y = 1 + 0 to numberOfPoints / 128.0;
            Autodiffer.EvaluateSimpleExponentFunctionMultipleTimes(numberOfPoints);

            Console.ReadKey();
        }
    }
}
