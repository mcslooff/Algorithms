package nl.tufa.fraction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.tufa.common.MathUtils;

/**
 * <p>
 * The <code>final</code> immutable <code>Fraction</code> class implements
 * fractions and mathematical operations on fractions such as Least Common
 * Multiple (LCM), Largest Common Divider (GCD), multiplication, division,
 * addition and subtraction. It does so by using integer calculations (of type
 * Long).
 * </p>
 * <p>
 * Both denominator and divisor are of type Long. ArithematicExceptions are
 * thrown on division by zero errors and when the product of values exceeds the
 * Long.MAX_VALUE
 * </p>
 * <p>
 * Fractions are normalized on instantiation, e.g. 2/4 will be represented as
 * 1/2, 9/6 as 3/2 etc.
 * </p>
 * 
 * @author M.C.Slooff
 * @version 1.0
 */
public final class Fraction extends Number implements Comparable<Fraction> {

	private static final long serialVersionUID = -5509503079152507363L;
	public final static double C_GRANULARITY = .00000000000001;

	private final Long denominator;
	private final Long divider;

	/**
	 * Creates an instance of <code>Fraction</code> with denominator and fraction.
	 * The specified divider may not be zero and neither divider nor denominator may
	 * be <code>null</code>. On instantiation the GCD of denominator and divider is
	 * calculated and both divider and denominator are divided by the GCD. When
	 * divider and/or denominator are negative the <code>Fraction</code> is modified
	 * in such a way that the mathematical result is correct and positive where
	 * possible. Sign of the fraction (if negative) is imposed on the denominator
	 * rather than on the divider.
	 * 
	 * @param denominator
	 *            non <code>null</code> <code>Long</code> value.
	 * @param divider
	 *            non <code>null</code> and non zero <code>Long</code> value.
	 * @throws ArithmeticException
	 *             when divider is zero.
	 */
	public Fraction(Long denominator, Long divider) throws ArithmeticException {

		if (denominator == null || divider == null || divider == 0)
			throw new ArithmeticException("Denominator and Divider may not be null and divisor may not be zero.");

		long d = 1;
		if (denominator != 0) {
			// Always calculate the GCD of absolute numbers.
			d = MathUtils.gcd(Math.abs(denominator), Math.abs(divider));
		} else {

		}

		// When both divider and denominator are negative numbers make sure
		// the GCD is negative. This will remove the sign from both sides.
		if (denominator < 0 && divider < 0) {
			d = -d;
		}

		// Make sure the sign of the fraction is on the denominator and not on the
		// divider. This is easier for compareTo method. We then only have to equalize
		// the divisors and compare the denominators. It's also nicer when printing
		// the value in a toString that the sign is the first character in the string.
		long signSwap = (divider < 0 && denominator >= 0 ? -1 : 1);

		this.denominator = (long) (signSwap * denominator / d);
		this.divider = (long) (signSwap * (denominator == 0 ? 1 : divider) / d);
	}

	/**
	 * Returns the denominator of a Fraction.
	 * 
	 * @return
	 */
	public Long getDenominator() {
		return this.denominator;
	}

	/**
	 * Returns the divider of a Fraction.
	 * 
	 * @return
	 */
	public Long getDivider() {
		return this.divider;
	}

	/**
	 * Multiplies two Fractions.
	 * 
	 * @param f
	 *            <code>Fraction</code> with which this instance is multiplied.
	 * @return The product of two <code>Fraction</code>s.
	 * @throws ArithmeticException
	 *             when <code>a * c</code> or <code>b * d</code> where
	 *             <code>f1=a/b</code> and <code>f2=c/d</code> yields a result that
	 *             exceeds the limits of <code>Long</code>
	 */
	public Fraction mul(Fraction f) throws ArithmeticException {

		if ((denominator + f.getDenominator()) / 2 > Math.sqrt(Long.MAX_VALUE)) {
			throw new ArithmeticException(
					"Multiplication of '" + denominator + "' and '" + f.getDenominator() + "' exceeds Long.");
		}

		if ((divider + f.getDivider()) / 2 > Math.sqrt(Long.MAX_VALUE)) {
			throw new ArithmeticException(
					"Multiplication of '" + divider + "' and '" + f.getDivider() + "' exceeds Long.");
		}

		return new Fraction(denominator * f.getDenominator(), divider * f.getDivider());
	}

	/**
	 * Divides two Fractions.
	 * 
	 * @param f
	 *            <code>Fraction</code> by which this instance is divided.
	 * @return The division of two <code>Fraction</code>s.
	 * @throws ArithmeticException
	 *             when <code>a * d</code> or <code>b * c</code> where
	 *             <code>f1=a/b</code> and <code>f2=c/d</code> yields a result that
	 *             exceeds the limits of <code>Long</code>. Note:
	 *             <code>(a/b)/(c/d) = (a/b)*(d/c)</code>
	 */
	public Fraction div(Fraction f) throws ArithmeticException {
		return new Fraction(denominator * f.getDivider(), divider * f.getDenominator());
	}

	/**
	 * Divides one Fraction by another Fraction for in Integer multiple times and
	 * returns the remaining Fraction.
	 * 
	 * @param f
	 * @return
	 * @throws ArithmeticException
	 */
	public Fraction mod(Fraction f) throws ArithmeticException {

		if ((divider + f.getDenominator()) / 2 > Math.sqrt(Long.MAX_VALUE)) {
			throw new ArithmeticException(
					"Multiplication of '" + divider + "' and '" + f.getDenominator() + "' exceeds Long.");
		}

		if ((denominator + f.getDivider()) / 2 > Math.sqrt(Long.MAX_VALUE)) {
			throw new ArithmeticException(
					"Multiplication of '" + denominator + "' and '" + f.getDivider() + "' exceeds Long.");
		}

		if ((divider + f.getDivider()) / 2 > Math.sqrt(Long.MAX_VALUE)) {
			throw new ArithmeticException(
					"Multiplication of '" + divider + "' and '" + f.getDivider() + "' exceeds Long.");
		}

		return new Fraction((denominator * f.getDivider()) % (f.getDenominator() * divider), divider * f.getDivider());

	}

	/**
	 * Calculated the Greatest Common Divider of two Fractions.
	 * 
	 * @param f
	 * @return
	 * @throws ArithmeticException
	 */
	public Fraction gcd(Fraction f) throws ArithmeticException {

		if ((divider + f.getDenominator()) / 2 > Math.sqrt(Long.MAX_VALUE)) {
			throw new ArithmeticException(
					"Multiplication of '" + divider + "' and '" + f.getDenominator() + "' exceeds Long.");
		}

		if ((denominator + f.getDivider()) / 2 > Math.sqrt(Long.MAX_VALUE)) {
			throw new ArithmeticException(
					"Multiplication of '" + denominator + "' and '" + f.getDivider() + "' exceeds Long.");
		}

		if ((divider + f.getDivider()) / 2 > Math.sqrt(Long.MAX_VALUE)) {
			throw new ArithmeticException(
					"Multiplication of '" + divider + "' and '" + f.getDivider() + "' exceeds Long.");
		}

		return new Fraction(MathUtils.gcd(divider * f.getDenominator(), denominator * f.divider),
				divider * f.getDivider());
	}

	/**
	 * Calculates the Least Common Multiple of two Fractions.
	 * 
	 * @param f
	 * @return
	 */
	public Fraction lcm(Fraction f) {

		long div = MathUtils.lcm(divider, f.getDivider());
		long den = MathUtils.lcm(div / divider * denominator, div / f.getDivider() * f.getDenominator());

		return new Fraction(den, div);
	}

	/**
	 * Adds two fractions by equalizing the denominators.
	 * 
	 * @param f
	 * @return
	 */
	public Fraction add(Fraction f) {
		long div = MathUtils.lcm(divider, f.getDivider());
		return new Fraction(div / divider * denominator + div / f.getDivider() * f.getDenominator(), div);
	}

	/**
	 * Substacts two fraction by equalizing the denominators.
	 * 
	 * @param f
	 * @return
	 */
	public Fraction sub(Fraction f) {
		long div = MathUtils.lcm(divider, f.getDivider());
		return new Fraction(div / divider * denominator - div / f.getDivider() * f.getDenominator(), div);
	}

	/**
	 * Parses a String into a Fraction. Both denominator and divider may have a sign
	 * (+ or -). Denominator and divider must be separated by a single forward slash
	 * '/'. Sign symbols may not be separated from the numbers by white spaces.
	 * Denominator, divider and forward slash may be separated by white spaces.
	 * 
	 * @param fraction
	 * @return
	 * @throws NumberFormatException
	 */
	public static Fraction parseFraction(String fraction) throws NumberFormatException {

		Pattern p = Pattern.compile("^\\s*([\\+\\-]?\\d+)\\s*$");
		String[] s = fraction.split("/");

		if (s.length != 2)
			throw new NumberFormatException("No denominator found in String.");

		Matcher m = p.matcher(s[0]);
		if (!m.matches())
			throw new NumberFormatException("'" + s[0] + " is not a valid Long number.");
		Long denominator = Long.parseLong(m.group(1));

		m = p.matcher(s[1]);
		if (!m.matches())
			throw new NumberFormatException("'" + s[1] + " is not a valid Long number.");
		Long divider = Long.parseLong(m.group(1));

		return new Fraction(denominator, divider);
	}

	/**
	 * Returns the inverse of the fraction, e.g. inv(1/2) returns 2/1, inv(3/2)
	 * returns 2/3. Throws an ArithemticException when the denominator is zero, e.g.
	 * inv(0/1) throws ArithemticException.
	 * 
	 * @return
	 * @throws ArithmeticException
	 */
	public Fraction inv() throws ArithmeticException {
		return new Fraction(divider, denominator);
	}

	/**
	 * Returns true if and only if the Fraction does not contain a fractional part
	 * e.g.: 4/2 or 9/3.
	 * 
	 * @return
	 */
	public boolean isInteger() {
		return (Math.abs(denominator) == Math.abs(divider));
	}

	/**
	 * Returns true if and only if the denominator is larger than the divisor e.g.
	 * 5/3.
	 * 
	 * @return
	 */
	public boolean hasInteger() {
		return (Math.abs(denominator) > Math.abs(divider));
	}

	/**
	 * Return the integer part of the Fraction e.g. returns 2 when fraction equals
	 * 5/2.
	 * 
	 * @return
	 */
	public Long getInteger() {
		return denominator / divider;
	}

	/**
	 * Returns a String representation of the Fraction in the form "1/2"
	 */
	@Override
	public String toString() {
		return denominator.toString() + "/" + divider.toString();
	}

	/**
	 * <p>
	 * Attempts to construct a <code>Fraction</code> from a <code>double</code>. The
	 * <code>Fraction</code> in worst case is an approximation of the decimal number
	 * limited by <code>C_GRANULARITY</code> and the number of recursive calls (30)
	 * to determine an appropriate faction.
	 * </p>
	 * <p>
	 * The fraction is approximated by recursively calling the <code>valueOf</code>
	 * function with the decimal part of <code>1/x</code> calculated by
	 * <code>(1/x-Math.floor(1/x))</code> until <code>1/x</code> yields an integer
	 * result, with rounding to 0.00000000000001 and a maximum recursive depth of
	 * 30. The algorithm obtains correct result on <code>Math.sqrt(2)</code> and
	 * <code>Math.PI</code> with deviations of 0.0 on the granularity of
	 * <code>double</code>. Example:
	 * <ul>
	 * <li>11/13 ~ 0,8461538461538461.....
	 * <li>1 / (11/13) = 13/11 - 1 = 2/11 (<code>x-Math.floor(x)</code>)
	 * <li>pass 2/11 into valueOff function.
	 * <ul>
	 * <li>2/11 ~ 0,181818181818.....
	 * <li>1/ (2/11) = 11/2 - 5 = 1/2
	 * <li>pass 1/2 into valueOf function.
	 * <ul>
	 * <li>1/2 = 0.5
	 * <li>1 / (1/2) = 2 (this is an integer value)
	 * <li>return 1/2 as Fraction.
	 * </ul>
	 * <li>with 1/2 do 2 / (2/11) = 11 (this is an integer value)
	 * <li>return 2/11 as Fraction.
	 * </ul>
	 * <li>with 2/11 do 11 / (11/13) = 13 (this is an integer value)
	 * <li>return 11/13 as fraction.
	 * </ul>
	 * </p>
	 * <p>
	 * To check whether or not a value is an integer there is rounding involved. The
	 * real number is rounded to the closest multiple of <code>C_GRANULARITY</code>
	 * by employing the following formula:
	 * <code>y = Math.round(x/C_GRANULARITY)*C_GRANULARITY</code>. When
	 * <code>y</code> equals <code>Math.floor(y)</code> the result <code>y</code> is
	 * treated as an integer.
	 * </P>
	 * <p>
	 * After 30 recursive calls to <code>valueOf()</code> function aborts returning
	 * the <code>1/Math.floor(1/x)</code> as fraction. Executing more than 32
	 * recursive calls will cause the JVM to throw an Exception. Hence we have to
	 * limit the recursion-depth.
	 * </p>
	 * 
	 * @param fraction
	 * @return
	 */
	public static Fraction valueOf(double fraction) {

		return fromDouble(fraction, 30);
	}

	private static Fraction fromDouble(double fraction, int maxRecursion) {

		double x = 1 / fraction;

		if (maxRecursion == 0)
			return new Fraction(1L, Math.round(x));

		if (Math.floor(x) == MathUtils.roundTo(x, C_GRANULARITY)) {
			return new Fraction(1L, (long) x);
		}

		if ((x - Math.floor(x)) < C_GRANULARITY || (1 - (x - Math.floor(x))) < C_GRANULARITY) {
			return new Fraction(1L, Math.round(x));
		}

		Fraction y = fromDouble(x - Math.floor(x), maxRecursion - 1);

		return new Fraction(y.getDivider(), Math.round(y.getDivider() / fraction));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((denominator == null) ? 0 : denominator.hashCode());
		result = prime * result + ((divider == null) ? 0 : divider.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fraction other = (Fraction) obj;
		if (denominator == null) {
			if (other.denominator != null)
				return false;
		} else if (!denominator.equals(other.denominator))
			return false;
		if (divider == null) {
			if (other.divider != null)
				return false;
		} else if (!divider.equals(other.divider))
			return false;
		return true;
	}

	@Override
	public double doubleValue() {
		return (double) denominator / (double) divider;
	}

	@Override
	public float floatValue() {
		return (float) denominator / (float) divider;
	}

	@Override
	public int intValue() {
		return (int) doubleValue();
	}

	@Override
	public long longValue() {
		return (long) doubleValue();
	}

	@Override
	public int compareTo(Fraction f) {

		if (f == null)
			return 1;
		long div = MathUtils.lcm(divider, f.getDivider());
		return new Long(div / divider * denominator).compareTo(div / f.getDivider() * f.getDenominator());
	}

}
