package cz.eowyn.srgen.io;

// This file contents were taken from pcgen

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

//import pcgen.io.exporttoken.EqToken;
//import pcgen.io.exporttoken.EqTypeToken;
//import pcgen.core.utils.CoreUtility;

import cz.eowyn.srgen.io.FORNode;
import cz.eowyn.srgen.io.FileAccess;
import cz.eowyn.srgen.io.IIFNode;

import cz.eowyn.srgen.model.PlayerCharacter;
import cz.eowyn.srgen.exporttoken.*;


public class ExportHandler {

	private static boolean existsOnly = false;
	private static boolean noMoreItems = false;
	private final Map loopVariables = new HashMap();
	private static HashMap tokenMap = new HashMap();
	private File templateFile;
	private boolean canWrite = true;
	//private boolean inLabel = false;

	public ExportHandler (File templateFile) {
		populateTokenMap ();
		setTemplateFile(templateFile);
	}

	protected void populateTokenMap() {
		if (tokenMap.isEmpty()) {
			addToTokenMap (new AttrToken ());
			addToTokenMap (new ExportToken ());
			addToTokenMap (new NameToken ());
			addToTokenMap (new PoolsToken ());
			addToTokenMap (new VitalsToken ());
			addToTokenMap (new EdgeToken ());
			addToTokenMap (new SkillToken ());
		}
	}
	
	public void write(PlayerCharacter pc, BufferedWriter out) {
		//FileAccess.setCurrentOutputFilter(templateFile.getName());

		// Reading a template
		BufferedReader br = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(templateFile), "UTF-8"));

			String aString;
			final StringBuffer inputLine = new StringBuffer();

			while ((aString = br.readLine()) != null)
			{
				if (aString.length() == 0)
				{
					inputLine.append(' ').append("\n");
				}
				else if (aString.indexOf("||") < 0)
				{
					inputLine.append(aString).append("\n");
				}
				else
				{
					// Adjacent separators get merged by StringTokenizer, so we break them up here
					int dblBarPos = aString.indexOf("||");

					while (dblBarPos >= 0)
					{
						inputLine.append(aString.substring(0, dblBarPos)).append("| |");
						aString = aString.substring(dblBarPos + 2);
						dblBarPos = aString.indexOf("||");
					}

					if (aString.length() > 0)
					{
						inputLine.append(aString);
					}

					inputLine.append("\n");
				}
			}

			aString = inputLine.toString();

			final StringTokenizer aTok = new StringTokenizer(aString, "\r\n", false);

			final FORNode root = parseFORs(aTok);
			loopVariables.put(null, "0");
			existsOnly = false;
			noMoreItems = false;

			final FileAccess fa = new FileAccess();

			//
			// now actualy process the (new) template file
			//
			//replaceLine(aString, out, pc);
			loopFOR(root, 0, 0, 1, out, fa, pc);
			loopVariables.clear();
		}
		catch (IOException exc)
		{
			//Logging.errorPrint("Error in ExportHandler::write", exc);
			System.err.println("Error in ExportHandler::write: " + exc.getMessage());
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					//TODO: If this should be ignored, add a comment here describing why. XXX
				}
			}

			if (out != null)
			{
				try
				{
					out.flush();
				}
				catch (IOException e)
				{
					//TODO: If this should be ignored, add a comment here describing why. XXX
				}
			}
		}

		
		}	
	
	private void addToTokenMap(Token newToken) {
		Object test = tokenMap.put (newToken.getTokenName (), newToken);

		if (test != null) {
			//Logging.errorPrint("More then one Output Token has the same Token Name: '" + newToken.getTokenName() + "'");
			System.err.println ("More then one Output Token has the same Token Name: '" + newToken.getTokenName() + "'");
		}
	}
	
	private void setTemplateFile(File templateFile)
	{
		this.templateFile = templateFile;
	}
	
	private void replaceLine(String aLine, BufferedWriter output, PlayerCharacter aPC)
	{
		boolean inPipe = false;
		boolean flag;
		StringBuffer tokString = new StringBuffer("");

		if (!inPipe && (aLine.lastIndexOf('|') < 0))
		{
			if (aLine.length() > 0)
			{
				outputNonToken(aLine, output);
			}
		}
		else if ((inPipe && (aLine.lastIndexOf('|') < 0)) || (!inPipe && (aLine.lastIndexOf('|') == 0)))
		{
			tokString.append(aLine.substring(aLine.lastIndexOf('|') + 1));
			inPipe = true;
		}
		else
		{
			if (!inPipe && (aLine.charAt(0) == '|'))
			{
				inPipe = true;
			}

			final StringTokenizer bTok = new StringTokenizer(aLine, "|", false);
			flag = bTok.countTokens() == 1;

			while (bTok.hasMoreTokens())
			{
				String bString = bTok.nextToken();

				if (!inPipe)
				{
					outputNonToken(bString, output);
				}
				else
				{
					if (bTok.hasMoreTokens() || flag
					    || (inPipe && !bTok.hasMoreTokens() && (aLine.charAt(aLine.length() - 1) == '|')))
					{
						replaceToken(tokString.toString() + bString, output, aPC);
						tokString = new StringBuffer("");
					}
					else
					{
						tokString.append(bString);
					}
				}

				if (bTok.hasMoreTokens() || flag)
				{
					inPipe = !inPipe;
				}
			}

			if (inPipe && (aLine.charAt(aLine.length() - 1) == '|'))
			{
				inPipe = false;
			}
		}
	}
	
	private void outputNonToken(String aString, BufferedWriter output)
	{
		//If something shouldn't be output, return.
		if (!canWrite)
		{
			return;
		}

		if (aString.length() > 0)
		{
			FileAccess.write(output, aString);
		}
	}
	
	public int replaceToken(String aString, BufferedWriter output, PlayerCharacter aPC)
	{
		try
		{
			int len = 1;

			if (!canWrite && (aString.length() > 0) && (aString.charAt(0) != '%'))
			{
				return 0;
			}

//			if ("%".equals(aString))
//			{
//				inLabel = false;
//				canWrite = true;
//
//				return 0;
//			}

			FileAccess.maxLength(-1);

			//
			// Start the |%blah| token section
			//
//			if ((aString.length() > 0) && (aString.charAt(0) == '%') && (aString.length() > 1)
//			    && (aString.lastIndexOf('<') < 0) && (aString.lastIndexOf('>') < 0))
//			{
//				boolean found = false;
//				canWrite = true;

//				if (aString.substring(1).startsWith("VAR."))
//				{
//					replaceTokenVar(aString, aPC);
//					return 0;
//				}
//
//				if (aString.substring(1).startsWith("COUNT["))
//				{
//					if (getVarValue(aString.substring(1), aPC) > 0)
//					{
//						canWrite = true;
//
//						return 1;
//					}
//
//					canWrite = false;
//
//					return 0;
//				}

//				if (found)
//				{
//					inLabel = true;
//
//					return 0;
//				}
//				else
//				{
//					canWrite = false;
//					inLabel = true;
//
//					return 0;
//				}
//			}

			//
			// now check for the rest of the tokens
			//
			if (tokenMap.isEmpty())
			{
				populateTokenMap();
			}

			StringTokenizer tok = new StringTokenizer(aString, ".,", false);
			String firstToken = tok.nextToken();

			String testString = aString;
			if (testString.indexOf(',') > -1)
			{
				testString = testString.substring(0, testString.indexOf(','));
			}
			if(testString.indexOf('~') > -1)
			{
				testString = testString.substring(0, testString.indexOf('~'));
			}

//			//Leave
//			if (aString.startsWith("FOR.") || aString.startsWith("DFOR."))
//			{
//				FileAccess.maxLength(-1);
//
//				existsOnly = false;
//				noMoreItems = false;
//				checkBefore = false;
//
//				//skipMath = true;
//				replaceTokenForDfor(aString, output, aPC);
//
//				//skipMath = false;
//				existsOnly = false;
//				noMoreItems = false;
//
//				return 0;
//			}
//
//			//Leave
			if (aString.startsWith("OIF("))
			{
				replaceTokenIIF(aString, output, aPC);
			}
//
//			//Leave
//			else if (((testString.indexOf('(') >= 0) || (testString.indexOf('+') >= 0) || (testString.indexOf('-') >= 0)
//			    || (testString.indexOf(".INTVAL") >= 0) || (testString.indexOf(".SIGN") >= 0)
//			    || (testString.indexOf(".NOZERO") >= 0) || (testString.indexOf(".TRUNC") >= 0) || (testString.indexOf('*') >= 0)
//			    || (testString.indexOf('/') >= 0)) && (!skipMath) && (doMathMode))
//			{
//				FileAccess.maxLength(-1);
//				FileAccess.write(output, mathMode(aString, aPC));
//
//				return 0;
//			}

			//Leave
			else if (tokenMap.get(firstToken) != null)
			{
				Token token = (Token) tokenMap.get(firstToken);
				if (token.getTokenName().equals("NOTE")
					|| token.getTokenName().startsWith("BIO")
					|| token.getTokenName().startsWith("DESC")
					|| token.getTokenName().startsWith("EQ"))
				{
					FileAccess.write(output, token.getToken(aString, aPC));
				}
				else
				{
					//FileAccess.encodeWrite(output, token.getToken(aString, aPC, this));
					FileAccess.write(output, token.getToken(aString, aPC));
				}
			}


			else
			{
				len = aString.trim().length();

				if (aString.length() > 0)
				{
					FileAccess.write(output, aString);

					// This is _NOT_ an error or even debug
					// worthy. Please no Logging here!
				}
			}

			FileAccess.maxLength(-1);

			return len;
		}
		catch (Exception exc)
		{
			System.err.println("Error replacing " + aString);
			return 0;
		}
	}

	private int getVarValue(String var, PlayerCharacter aPC)
	{
		char chC;

		System.err.println("getVarValue: " + var);

		//		for (int idx = -1;;)
//		{
//			idx = var.indexOf("COUNT[EQ", idx + 1);
//
//			if (idx < 0)
//			{
//				break;
//			}
//
//			chC = var.charAt(idx + 8);
//
//			if ((chC == '.') || ((chC >= '0') && (chC <= '9')))
//			{
//				final int i = var.indexOf(']', idx + 8);
//
//				if (i >= 0)
//				{
//					String aString = var.substring(idx + 6, i);
//					if(aString.indexOf("EQTYPE") > -1) {
//						EqTypeToken token = new EqTypeToken();
//						aString = token.getToken(aString, aPC);
//					}
//					else {
//						EqToken token = new EqToken();
//						aString = token.getToken(aString, aPC);
//					}
//					var = var.substring(0, idx) + aString + var.substring(i + 1);
//				}
//			}
//		}

		for (int idx = -1;;)
		{
			idx = var.indexOf("STRLEN[", idx + 1);

			if (idx < 0)
			{
				break;
			}

			final int i = var.indexOf(']', idx + 7);

			if (i >= 0)
			{
				String aString = var.substring(idx + 7, i);
				StringWriter sWriter = new StringWriter();
				BufferedWriter aWriter = new BufferedWriter(sWriter);
				replaceToken(aString, aWriter, aPC);
				sWriter.flush();

				try
				{
					aWriter.flush();
				}
				catch (IOException e)
				{
					//TODO: If this should be ignored, add a comment here describing why. XXX
				}

				aString = sWriter.toString();
				var = var.substring(0, idx) + aString.length() + var.substring(i + 1);
			}
		}

		//return aPC.getVariableValue(var, "").intValue();
		try {
			return Integer.parseInt(var);
		}
		catch (Exception e) {
			// pass on
		}

	
		StringWriter sWriter = new StringWriter();
		BufferedWriter aWriter = new BufferedWriter(sWriter);
		replaceToken(var, aWriter, aPC);
		sWriter.flush();

		try
		{
			aWriter.flush();
		}
		catch (IOException e)
		{
			//TODO: If this should be ignored, add a comment here describing why. XXX
		}

		var = sWriter.toString();
		try {
			return Integer.parseInt(var);
		}
		catch (Exception e) {
			return 0;
		}
	}

	
	private FORNode parseFORs(StringTokenizer tokens)
	{
		final FORNode root = new FORNode(null, "0", "0", "1", true);
		String line;

		while (tokens.hasMoreTokens())
		{
			line = tokens.nextToken();

			if (line.startsWith("|FOR"))
			{
				StringTokenizer newFor = new StringTokenizer(line, ",");

				if (newFor.countTokens() > 1)
				{
					newFor.nextToken();

					if (newFor.nextToken().startsWith("%"))
					{
						root.addChild(parseFORs(line, tokens));
					}
					else
					{
						root.addChild(line);
					}
				}
				else
				{
					root.addChild(line);
				}
			}
			else if (line.startsWith("|IIF(") && (line.lastIndexOf(',') < 0))
			{
				String expr = line.substring(5, line.lastIndexOf(')'));
				root.addChild(parseIIFs(expr, tokens));
			}
			else
			{
				root.addChild(line);
			}
		}

		return root;
	}

	private FORNode parseFORs(String forLine, StringTokenizer tokens)
	{
		final StringTokenizer forVars = new StringTokenizer(forLine, ",");
		forVars.nextToken();

		final String var = forVars.nextToken();
		final String min = forVars.nextToken();
		final String max = forVars.nextToken();
		final String step = forVars.nextToken();
		final String eTest = forVars.nextToken();
		boolean exists = false;

		System.out.println("parseFORs: " + var + " " + min + " " + max + " " + step + " " + eTest);
		
		if (((eTest.length() > 0) && (eTest.charAt(0) == '1')) || ((eTest.length() > 0) && (eTest.charAt(0) == '2')))
		{
			exists = true;
		}

		final FORNode node = new FORNode(var, min, max, step, exists);
		String line;

		while (tokens.hasMoreTokens())
		{
			line = tokens.nextToken();

			if (line.startsWith("|FOR"))
			{
				StringTokenizer newFor = new StringTokenizer(line, ",");
				newFor.nextToken();

				if (newFor.nextToken().startsWith("%"))
				{
					node.addChild(parseFORs(line, tokens));
				}
				else
				{
					node.addChild(line);
				}
			}
			else if (line.startsWith("|IIF(") && (line.lastIndexOf(',') < 0))
			{
				String expr = line.substring(5, line.lastIndexOf(')'));
				node.addChild(parseIIFs(expr, tokens));
			}
			else if (line.startsWith("|ENDFOR|"))
			{
				return node;
			}
			else
			{
				node.addChild(line);
			}
		}

		return node;
	}

	private IIFNode parseIIFs(String expr, StringTokenizer tokens)
	{
		final IIFNode node = new IIFNode(expr);
		String line;
		boolean childrenType = true;

		while (tokens.hasMoreTokens())
		{
			line = tokens.nextToken();

			if (line.startsWith("|FOR"))
			{
				StringTokenizer newFor = new StringTokenizer(line, ",");
				newFor.nextToken();

				if (newFor.nextToken().startsWith("%"))
				{
					if (childrenType)
					{
						node.addTrueChild(parseFORs(line, tokens));
					}
					else
					{
						node.addFalseChild(parseFORs(line, tokens));
					}
				}
				else
				{
					if (childrenType)
					{
						node.addTrueChild(line);
					}
					else
					{
						node.addFalseChild(line);
					}
				}
			}
			else if (line.startsWith("|IIF(") && (line.lastIndexOf(',') < 0))
			{
				String newExpr = line.substring(5, line.lastIndexOf(')'));

				if (childrenType)
				{
					node.addTrueChild(parseIIFs(newExpr, tokens));
				}
				else
				{
					node.addFalseChild(parseIIFs(newExpr, tokens));
				}
			}
			else if (line.startsWith("|ELSE|"))
			{
				childrenType = false;
			}
			else if (line.startsWith("|ENDIF|"))
			{
				return node;
			}
			else
			{
				if (childrenType)
				{
					node.addTrueChild(line);
				}
				else
				{
					node.addFalseChild(line);
				}
			}
		}

		return node;
	}

	private void replaceTokenIIF(String aString, BufferedWriter output, PlayerCharacter aPC)
	{
		int iParenCount = 0;
		final String[] aT = new String[3];
		int i;
		int iParamCount = 0;
		int iStart = 4;

		// OIF(expr,truepart,falsepart)
		// {|OIF(HASFEAT:Armor Prof (Light), <b>Yes</b>, <b>No</b>)|}
		for (i = iStart; i < aString.length(); ++i)
		{
			if (iParamCount == 3)
			{
				break;
			}

			switch (aString.charAt(i))
			{
				case '(':
					iParenCount += 1;

					break;

				case ')':
					iParenCount -= 1;

					if (iParenCount == -1)
					{
						if (iParamCount == 2)
						{
							aT[iParamCount++] = aString.substring(iStart, i).trim();
							iStart = i + 1;
						}
						else
						{
//							Logging.errorPrint("IIF: not enough parameters (" + Integer.toString(iParamCount) + ')');
							System.err.println ("IIF: not enough parameters (" + Integer.toString(iParamCount) + ')');
							for (int j = 0; j < iParamCount; ++j)
							{
//								Logging.errorPrint("  " + Integer.toString(j) + ':' + aT[j]);
								System.err.println ("  " + Integer.toString(j) + ':' + aT[j]);
							}
						}
					}

					break;

				case ',':

					if (iParenCount == 0)
					{
						if (iParamCount < 2)
						{
							aT[iParamCount] = aString.substring(iStart, i).trim();
							iStart = i + 1;
						}
						else
						{
//							Logging.errorPrint("IIF: too many parameters");
							System.err.println ("IIF: too many parameters");
						}

						iParamCount += 1;
					}

					break;

				default:
					break;
			}
		}

		if (iParamCount != 3)
		{
//			Logging.errorPrint("IIF: invalid parameter count: " + iParamCount);
			System.err.println ("IIF: invalid parameter count: " + iParamCount);
		}
		else
		{
			aString = aString.substring(iStart);
			iStart = 2;

			if (evaluateExpression(aT[0], aPC))
			{
				iStart = 1;
			}

			FileAccess.write(output, aT[iStart]);
		}

		if (aString.length() > 0)
		{
//			Logging.errorPrint("IIF: extra characters on line: " + aString);
			System.err.println ("IIF: extra characters on line: " + aString);
			FileAccess.write(output, aString);
		}
	}
	
	private boolean evaluateExpression(String expr, PlayerCharacter aPC)
	{
		if (expr.indexOf(".AND.") > 0)
		{
			String part1 = expr.substring(0, expr.indexOf(".AND."));
			String part2 = expr.substring(expr.indexOf(".AND.") + 5);

			return (evaluateExpression(part1, aPC) && evaluateExpression(part2, aPC));
		}

		if (expr.indexOf(".OR.") > 0)
		{
			String part1 = expr.substring(0, expr.indexOf(".OR."));
			String part2 = expr.substring(expr.indexOf(".OR.") + 4);

			return (evaluateExpression(part1, aPC) || evaluateExpression(part2, aPC));
		}

		for (Iterator ivar = loopVariables.keySet().iterator(); ivar.hasNext();)
		{
			Object anObject = ivar.next();

			if (anObject == null)
			{
				continue;
			}

			String fString = anObject.toString();
			String rString = loopVariables.get(fString).toString();
			expr.replaceAll(fString, rString);
		}

//		if (expr.startsWith("HASVAR:"))
//		{
//			expr = expr.substring(7).trim();
//
//			return (aPC.getVariableValue(expr, "").intValue() > 0);
//		}
//
//		if (expr.startsWith("HASFEAT:"))
//		{
//			expr = expr.substring(8).trim();
//
//			return (aPC.getFeatNamed(expr) != null);
//		}
//
//		if (expr.startsWith("HASSA:"))
//		{
//			expr = expr.substring(6).trim();
//
//			return (aPC.hasSpecialAbility(expr));
//		}
//
//		if (expr.startsWith("HASEQUIP:"))
//		{
//			expr = expr.substring(9).trim();
//
//			return (aPC.getEquipmentNamed(expr) != null);
//		}


		if (expr.startsWith("EVEN:"))
		{
			int i = 0;

			try
			{
				i = Integer.parseInt(expr.substring(5).trim());
			}
			catch (NumberFormatException exc)
			{
				System.err.println("EVEN:" + i);

				return true;
			}

			return ((i % 2) == 0);
		}


		// Before returning false, let's see if this is a valid token, like this:
		//
		// |IIF(WEAPON%weap.CATEGORY:Ranged)|
		// something 1
		// |ELSE|
		// something 2
		// |END IF|
		// It can theorically be used with any valid token, doing an equal compare
		// (integer or string equalities are valid)
		StringTokenizer aTok = new StringTokenizer(expr, ":");
		final String token;
		final String equals;

		final int tokenCount = aTok.countTokens();
		if (tokenCount == 1)
		{
			token = expr;
			equals = "TRUE";
		}
		else if (tokenCount != 2)
		{
			System.err.println("evaluateExpression: Incorrect syntax (missing parameter)");

			return false;
		}
		else
		{
			token = aTok.nextToken();
			equals = aTok.nextToken().toUpperCase();
		}

		StringWriter sWriter = new StringWriter();
		BufferedWriter aWriter = new BufferedWriter(sWriter);
		replaceToken(token, aWriter, aPC);
		sWriter.flush();

		try
		{
			aWriter.flush();
		}
		catch (IOException ignore)
		{
			; // Don't have anything to do in this case
		}

		String aString = sWriter.toString();
//		if (token.startsWith("VAR."))
//		{
//			aString = token.substring(4);
//			aString = aPC.getVariableValue(token.substring(4), "").toString();
//		}

		try
		{
			// integer values
			final int i = Integer.parseInt(aString);

			return (i != Integer.parseInt(equals)) ? false : true;
		}
		catch (NumberFormatException e)
		{
			// String values
			return (aString.toUpperCase().indexOf(equals) < 0) ? false : true;
		}
	}
	
	
	private void evaluateIIF(IIFNode node, BufferedWriter output, FileAccess fa, PlayerCharacter aPC)
	{
		//
		// Comma is a delimiter for a higher-level parser, so we'll use a semicolon and replace it with a comma for
		// expressions like:
		// |IIF(VAR.IF(var("COUNT[SKILLTYPE=Strength]")>0;1;0):1)|
		//
		String aString = node.expr(); 
		aString.replaceAll(";", ",");
		if (evaluateExpression(aString, aPC))
		{
			evaluateIIFChildren(node.trueChildren(), output, fa, aPC);
		}
		else
		{
			evaluateIIFChildren(node.falseChildren(), output, fa, aPC);
		}
	}

	private void evaluateIIFChildren(final List children, BufferedWriter output, FileAccess fa, PlayerCharacter aPC)
	{
		for (int y = 0; y < children.size(); ++y)
		{
			if (children.get(y) instanceof FORNode)
			{
				FORNode nextFor = (FORNode) children.get(y);
				loopVariables.put(nextFor.var(), new Integer(0));
				existsOnly = nextFor.exists();

				String minString = nextFor.min();
				String maxString = nextFor.max();
				String stepString = nextFor.step();
				String fString;
				String rString;

				for (Iterator ivar = loopVariables.keySet().iterator(); ivar.hasNext();)
				{
					Object anObject = ivar.next();

					if (anObject == null)
					{
						continue;
					}

					fString = anObject.toString();
					rString = loopVariables.get(fString).toString();
					minString.replaceAll(fString, rString);
					maxString.replaceAll(fString, rString);
					stepString.replaceAll(fString, rString);
				}

				loopFOR(nextFor, getVarValue(minString, aPC), getVarValue(maxString, aPC), getVarValue(stepString, aPC), output, fa, aPC);
				existsOnly = nextFor.exists();
				loopVariables.remove(nextFor.var());
			}
			else if (children.get(y) instanceof IIFNode)
			{
				evaluateIIF((IIFNode) children.get(y), output, fa, aPC);
			}
			else
			{
				String lineString = (String) children.get(y);

				for (Iterator ivar = loopVariables.keySet().iterator(); ivar.hasNext();)
				{
					Object anObject = ivar.next();

					if (anObject == null)
					{
						continue;
					}

					String fString = anObject.toString();
					String rString = loopVariables.get(fString).toString();
					lineString.replaceAll(fString, rString);
				}

				replaceLine(lineString, output, aPC);

				// output a newline if output is allowed
				if (canWrite)
				{
					FileAccess.newLine(output);
				}
			}
		}
	}
	
	private void loopFOR(FORNode node, int min, int max, int step, BufferedWriter output, FileAccess fa, PlayerCharacter aPC)
	{
		System.out.println(String.valueOf(min) +" "+ String.valueOf(max) +" "+ String.valueOf(step));
		for (int x = min; x <= max; x += step)
		{
			loopVariables.put(node.var(), new Integer(x));

			for (int y = 0; y < node.children().size(); ++y)
			{
				if (node.children().get(y) instanceof FORNode)
				{
					FORNode nextFor = (FORNode) node.children().get(y);
					loopVariables.put(nextFor.var(), new Integer(0));
					existsOnly = nextFor.exists();

					String minString = nextFor.min();
					String maxString = nextFor.max();
					String stepString = nextFor.step();
					System.out.println("loopFOR: " + nextFor.var() + " " + nextFor.min() + " " + nextFor.max() + " " + nextFor.step());

					String fString;
					String rString;

					for (Iterator ivar = loopVariables.keySet().iterator(); ivar.hasNext();)
					{
						Object anObject = ivar.next();

						if (anObject == null)
						{
							continue;
						}

						fString = anObject.toString();
						rString = loopVariables.get(fString).toString();
						//minString = CoreUtility.replaceAll(minString, fString, rString);
						//maxString = CoreUtility.replaceAll(maxString, fString, rString);
						//stepString = CoreUtility.replaceAll(stepString, fString, rString);
System.out.println("Z1: f:"+ fString+ " r:" +rString+ " step:" +stepString);
						minString.replaceAll(fString, rString);
						maxString.replaceAll(fString, rString);
						stepString.replaceAll(fString, rString);
					}

					final int varMin = getVarValue(minString, aPC);
					final int varMax = getVarValue(maxString, aPC);
					final int varStep = getVarValue(stepString, aPC);
					loopFOR(nextFor, varMin, varMax, varStep, output, fa, aPC);
					existsOnly = node.exists();
					loopVariables.remove(nextFor.var());
				}
				else if (node.children().get(y) instanceof IIFNode)
				{
					evaluateIIF((IIFNode) node.children().get(y), output, fa, aPC);
				}
				else
				{
					String lineString = (String) node.children().get(y);

					for (Iterator ivar = loopVariables.keySet().iterator(); ivar.hasNext();)
					{
						Object anObject = ivar.next();

						if (anObject == null)
						{
							continue;
						}

						String fString = anObject.toString();
						String rString = loopVariables.get(fString).toString();
						lineString = lineString.replaceAll(fString, rString);
					}

					noMoreItems = false;
					replaceLine(lineString, output, aPC);

					// output a newline at the end of each
					// loop (only if output is allowed)
					if (canWrite)
					{
						FileAccess.newLine(output);
					}

					// break out of loop if no more items
					if (existsOnly && noMoreItems)
					{
						x = max + 1;
					}
				}
			}
		}
	}
	
}
