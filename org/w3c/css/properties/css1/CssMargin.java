//
// $Id$
// From Philippe Le Hegaret (Philippe.Le_Hegaret@sophia.inria.fr)
//
// (c) COPYRIGHT MIT and INRIA, 1997.
// Please first read the full copyright statement in file COPYRIGHT.html
/*
 * $Log$
 * Revision 1.4  2005/08/08 13:18:12  ylafon
 * All those changed made by Jean-Guilhem Rouel:
 *
 * Huge patch, imports fixed (automatic)
 * Bug fixed: 372, 920, 778, 287, 696, 764, 233
 * Partial bug fix for 289
 *
 * Issue with "inherit" in CSS2.
 * The validator now checks the number of values (extraneous values were previously ignored)
 *
 * Revision 1.3  2003/10/20 13:15:49  ylafon
 * formatting
 *
 * Revision 1.2  2002/04/08 21:17:44  plehegar
 * New
 *
 * Revision 3.1  1997/08/29 13:13:53  plehegar
 * Freeze
 *
 * Revision 2.3  1997/08/26 13:56:28  plehegar
 * Added setSelectors()
 *
 * Revision 2.2  1997/08/20 11:41:25  plehegar
 * Freeze
 *
 * Revision 2.1  1997/08/08 15:52:25  plehegar
 * Nothing
 *
 * Revision 1.4  1997/08/06 17:30:10  plehegar
 * Updated set, now it's a constructor
 *
 * Revision 1.3  1997/07/30 13:20:09  plehegar
 * Updated package
 *
 * Revision 1.2  1997/07/24 01:36:58  plehegar
 * bug fix
 *
 * Revision 1.1  1997/07/24 01:07:08  plehegar
 * Initial revision
 *
 */
package org.w3c.css.properties.css1;

import org.w3c.css.parser.CssPrinterStyle;
import org.w3c.css.parser.CssSelectors;
import org.w3c.css.parser.CssStyle;
import org.w3c.css.util.ApplContext;
import org.w3c.css.util.InvalidParamException;
import org.w3c.css.values.CssExpression;
import org.w3c.css.values.CssOperator;

/**
 *   <H4>
 *     &nbsp;&nbsp; 'margin'
 *   </H4>
 *   <P>
 *   <EM>Value:</EM> [ &lt;length&gt; | &lt;percentage&gt; | auto ]{1,4} <BR>
 *   <EM>Initial:</EM> not defined for shorthand properties<BR>
 *   <EM>Applies to:</EM> all elements<BR>
 *   <EM>Inherited:</EM> no<BR>
 *   <EM>Percentage values:</EM> refer to parent element's width<BR>
 *   <P>
 *   The 'margin' property is a shorthand property for setting 'margin-top',
 *   'margin-right' 'margin-bottom' and 'margin-left' at the same place in the
 *   style sheet.
 *   <P>
 *   If four length values are specified they apply to top, right, bottom and
 *   left respectively. If there is only one value, it applies to all sides, if
 *   there are two or three, the missing values are taken from the opposite side.
 *   <PRE>
 *   BODY { margin: 2em } / * all margins set to 2em * /
 *   BODY { margin: 1em 2em } / * top &amp; bottom = 1em, right &amp; left = 2em * /
 *   BODY { margin: 1em 2em 3em } / * top=1em, right=2em, bottom=3em, left=2em * /
 * </PRE>
 *   <P>
 *   The last rule of the example above is equivalent to the example below:
 *   <PRE>
 *   BODY {
 *     margin-top: 1em;
 *     margin-right: 2em;
 *     margin-bottom: 3em;
 *     margin-left: 2em;        / * copied from opposite side (right) * /
 *   }
 * </PRE>
 *   <P>
 *   Negative margin values are allowed, but there may be implementation-specific
 *   limits.
 * @version $Revision$
 */
public class CssMargin extends CssProperty implements CssOperator {
    
    CssMarginTop top;
    CssMarginBottom bottom;
    CssMarginRight right;
    CssMarginLeft left;

    boolean inheritedValue;
    
    /**
     * Create a new CssMargin
     */
    public CssMargin() {
    }  
    
    /**
     * Create a new CssMargin
     *
     * @param expression The expression for this property
     * @exception InvalidParamException Values are incorrect
     */  
    public CssMargin(ApplContext ac, CssExpression expression, boolean check)  
	throws InvalidParamException {
	
	//CssValue val = expression.getValue();
	setByUser();

	/*if (val.equals(inherit)) {
	    inheritedValue = true;
	    top = new CssMarginTop();
	    top.value = inherit;
	    bottom = new CssMarginBottom();
	    bottom.value = inherit;
	    right = new CssMarginRight();
	    right.value = inherit;
	    left = new CssMarginLeft();
	    left.value = inherit;
	}*/	
	switch (expression.getCount()) {
	case 1:
	    top = new CssMarginTop(ac, expression);
	    bottom = new CssMarginBottom(top);
	    right = new CssMarginRight(top);
	    left = new CssMarginLeft(top);
	    break;
	case 2:
	    if (expression.getOperator() != SPACE)
		throw new InvalidParamException("operator",
			((new Character(expression.getOperator())).toString()),
			ac);
	    if(expression.getValue().equals(inherit)) {
		throw new InvalidParamException("unrecognize", ac);
	    }
	    top = new CssMarginTop(ac, expression);
	    if(expression.getValue().equals(inherit)) {
		throw new InvalidParamException("unrecognize", ac);
	    }
	    right = new CssMarginRight(ac, expression);
	    bottom = new CssMarginBottom(top);
	    left = new CssMarginLeft(right);
	    break;
	case 3:
	    if (expression.getOperator() != SPACE)
		throw new InvalidParamException("operator",
			((new Character(expression.getOperator())).toString()),
			ac);
	    if(expression.getValue().equals(inherit)) {
		throw new InvalidParamException("unrecognize", ac);
	    }
	    top = new CssMarginTop(ac, expression);
	    if (expression.getOperator() != SPACE)
		throw new InvalidParamException("operator",
			((new Character(expression.getOperator())).toString()),
			ac);
	    if(expression.getValue().equals(inherit)) {
		throw new InvalidParamException("unrecognize", ac);
	    }
	    right = new CssMarginRight(ac, expression);
	    if(expression.getValue().equals(inherit)) {
		throw new InvalidParamException("unrecognize", ac);
	    }
	    bottom = new CssMarginBottom(ac, expression);
	    left = new CssMarginLeft(right);
	    break;
	case 4:
	    if (expression.getOperator() != SPACE)
		throw new InvalidParamException("operator",
			((new Character(expression.getOperator())).toString()),
			ac);
	    if(expression.getValue().equals(inherit)) {
		throw new InvalidParamException("unrecognize", ac);
	    }
	    top = new CssMarginTop(ac, expression);
	    if (expression.getOperator() != SPACE)
		throw new InvalidParamException("operator",
			((new Character(expression.getOperator())).toString()),
			ac);
	    if(expression.getValue().equals(inherit)) {
		throw new InvalidParamException("unrecognize", ac);
	    }
	    right = new CssMarginRight(ac, expression);
	    if (expression.getOperator() != SPACE)
		throw new InvalidParamException("operator",
			((new Character(expression.getOperator())).toString()),
			ac);
	    if(expression.getValue().equals(inherit)) {
		throw new InvalidParamException("unrecognize", ac);
	    }
	    bottom = new CssMarginBottom(ac, expression);
	    if(expression.getValue().equals(inherit)) {
		throw new InvalidParamException("unrecognize", ac);
	    }
	    left = new CssMarginLeft(ac, expression);
	    break;
	default:
	    if(check)
		throw new InvalidParamException("unrecognize", ac);
	}
    }
    
    public CssMargin(ApplContext ac, CssExpression expression)
	throws InvalidParamException {
	this(ac, expression, false);
    }
    
    /**
     * Returns the value of this property
     */
    public Object get() {
	return top;
    }
    
    /**
     * Returns the top property
     */
    public CssMarginTop getTop() {
	return top;
    }
    
    /**
     * Returns the right property
     */
    public CssMarginRight getRight() {
	return right;
    }
    
    /**
     * Returns the bottom property
     */
    public CssMarginBottom getBottom() {
	return bottom;
    }
    
    /**
     * Returns the left property
     */
    public CssMarginLeft getLeft() {
	return left;
    }
    
    /**
     * Returns the name of this property
     */  
    public String getPropertyName() {
	return "margin";
    }
    
    /**
     * Returns a string representation of the object.
     */
    public String toString() {
	if (inheritedValue) {
	    return inherit.toString();
	}
	if (right.value.equals(left.value)) {
	    if (top.value.equals(bottom.value)) {
		if (top.value.equals(right.value)) {
		    return top.toString();
		} else {
		    return top + " " + right;
		}
	    } else {
		return top + " " + right + " " + bottom;
	    }
	} else {
	    return top + " " + right + " " + bottom + " " + left;
	}
    }
    
    /**
     * Set this property to be important.
     * Overrides this method for a macro
     */  
    public void setImportant() {
	if(top != null) {
	    top.important = true;
	}
	if(right != null) {
	    right.important = true;
	}
	if(bottom != null) {
	    bottom.important = true;
	}
	if(left != null) {
	    left.important = true;
	}
    }
    
    /**
     * Returns true if this property is important.
     * Overrides this method for a macro
     */
    public boolean getImportant() {
	return ((top == null || top.important) &&
		(right == null || right.important) &&
		(bottom == null || bottom.important) &&
		(left == null || left.important));
    }
    
    /**
     * Print this property.
     *
     * @see #toString()
     * @see #getPropertyName()
     */  
    public void print(CssPrinterStyle printer) {
	if (inheritedValue) {
	    printer.print(this);
	} else if ((top != null && right != null &&
		    bottom != null && left != null) &&
		   (getImportant() ||
		    (!top.important &&
		     !right.important &&
		     !bottom.important &&
		     !left.important))) {
	    printer.print(this);
	} else {
	    if (top != null)
		top.print(printer);
	    if (right != null)
		right.print(printer);
	    if (bottom != null)
		bottom.print(printer);
	    if (left != null)
		left.print(printer);
	}
	
    }
    
    /**
     * Set the context.
     * Overrides this method for a macro
     *
     * @see org.w3c.css.css.CssCascadingOrder#order
     * @see org.w3c.css.css.StyleSheetParser#handleRule
     */
    public void setSelectors(CssSelectors selector) {
	super.setSelectors(selector);
	if (top != null) {
	    top.setSelectors(selector);
	}
	if (right != null) {
	    right.setSelectors(selector);
	}
	if (bottom != null) {
	    bottom.setSelectors(selector);
	}
	if (left != null) {
	    left.setSelectors(selector);
	}
    }
    
    /**
     * Add this property to the CssStyle
     *
     * @param style The CssStyle
     */
    public void addToStyle(ApplContext ac, CssStyle style) {
	((Css1Style) style).cssMargin.inheritedValue = inheritedValue;
	if(top != null) {
	    top.addToStyle(ac, style);
	}
	if(right != null) {
	    right.addToStyle(ac, style);
	}
	if(bottom != null) {
	    bottom.addToStyle(ac, style);
	}
	if(left != null) {
	    left.addToStyle(ac, style);
	}
    }
    
    /**
     * Get this property in the style.
     *
     * @param style The style where the property is
     * @param resolve if true, resolve the style to find this property
     */  
    public CssProperty getPropertyInStyle(CssStyle style, boolean resolve) {
	if (resolve) {
	    return ((Css1Style) style).getMargin();
	} else {
	    return ((Css1Style) style).cssMargin;
	}
    }
    
    /**
     * Update the source file and the line.
     * Overrides this method for a macro
     *
     * @param line The line number where this property is defined
     * @param source The source file where this property is defined
     */  
    public void setInfo(int line, String source) {
	super.setInfo(line, source);
	// it assumes that values exists, that may not be the case
	// always. What would be the cause of that, an invalid clause?
	// in this case a proper exception should be sent 
	// So... a FIXME
	top.setInfo(line, source);
	right.setInfo(line, source);
	bottom.setInfo(line, source);
	left.setInfo(line, source);
    }
    
    /**
     * Compares two properties for equality.
     *
     * @param value The other property.
     */  
    public boolean equals(CssProperty property) {
	return false;
    }
    
}