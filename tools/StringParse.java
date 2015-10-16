package com.powerdata.openpa.tools;

import java.util.Vector;

public class StringParse
{
	static final String _WhiteSpace = " \t\n\r";
	String _whiteSpace;
	String _parseLine;
	String _delimiters;
	StringBuffer _token;
	boolean _delimiterFound = false;
	boolean _keepQuotes;
	int _nxtOfs;
	int _strlen;
	char _quoteChar = '"';
	boolean _mergeDelim = false;

	public StringParse(String bufferToParse, String delimiters,
			boolean keepQuotes)
	{
		_parseLine = bufferToParse;
		_delimiters = delimiters;
		_token = new StringBuffer();
		_keepQuotes = keepQuotes;
		_nxtOfs = 0;
		_strlen = _parseLine.length();
		_whiteSpace = _WhiteSpace;

	}

	public StringParse(String bufferToParse, String delimiters)
	{
		this(bufferToParse, delimiters, false);
	}

	public StringParse(String bufferToParse)
	{
		this(bufferToParse, _WhiteSpace, false);
	}

	public StringParse setWhiteSpace(String whiteSpace)
	{
		_whiteSpace = whiteSpace;
		return this;
	}

	public StringParse setQuoteChar(char qchar)
	{
		_quoteChar = qchar;
		return this;
	}
	
	public void setMergeDelimiters(boolean md) {_mergeDelim = md;}

	protected void advancePastWhitespace()
	{
		while ((_nxtOfs < _strlen) && (_whiteSpace.indexOf(_parseLine.charAt(_nxtOfs)) >= 0))
			++_nxtOfs;
	}
	protected void advancePastDelimiters()
	{
		while ((_nxtOfs < _strlen)	&& (_delimiters.indexOf(_parseLine.charAt(_nxtOfs)) >= 0))
			++_nxtOfs;		
	}

	public boolean hasMoreTokens()
	{
		return (_delimiterFound || _nxtOfs < _strlen);
	}

	public String nextToken(String delimiters)
	{
		// set the delimiters
		_delimiters = delimiters;
		// call next token
		return nextToken();
	}

	public String nextToken()
	{
		if (_mergeDelim) advancePastDelimiters();
		// advance to the next token
		advancePastWhitespace();
		// make sure we aren't done
		_delimiterFound = false;
		if (_nxtOfs >= _strlen) return "";
		// get the next character
		char nxtChar = _parseLine.charAt(_nxtOfs);
		char quoteChar = (nxtChar == '\'' || nxtChar == '"')?nxtChar:_quoteChar;
		// initalize booleans for tracking our parsing
		boolean quoteActive = false;
		boolean escapeActive = false;
		boolean endOfToken = false;
		_token.setLength(0);

		while (!endOfToken)
		{
			// check for the end of the token
			if ((_delimiters.indexOf(nxtChar) >= 0) && !quoteActive
					&& !escapeActive)
			{
				endOfToken = true;
				++_nxtOfs;
				_delimiterFound = true;
			}
			else
			{
				switch (nxtChar)
				{
				case '\r':
				case '\n': // if not quoted these are EOL
					if (quoteActive)
					{
						_token.append(nxtChar);
						break;
					}
				case 0: // is this still a terminator in java?
					endOfToken = true;
					break;
				case '\\': // either activates escape, or is litteral
					if (escapeActive)
					{
						_token.append(nxtChar);
						escapeActive = false;
					}
					else
					{
						escapeActive = true;
					}
					break;
				case 'a': // is an alarm char if escaped
					_token.append((escapeActive) ? '\07' : nxtChar);
					escapeActive = false;
					break;
				case 'b': // is a backspace char if escaped
					_token.append((escapeActive) ? '\b' : nxtChar);
					escapeActive = false;
					break;
				case 'f': // is a form feed if escaped
					_token.append((escapeActive) ? '\f' : nxtChar);
					escapeActive = false;
					break;
				case 'n': // is a newline if escaped
					_token.append((escapeActive) ? '\n' : nxtChar);
					escapeActive = false;
					break;
				case 'r': // is a return char if escaped
					_token.append((escapeActive) ? '\r' : nxtChar);
					escapeActive = false;
					break;
				case 't': // is a tab char if escaped
					_token.append((escapeActive) ? '\t' : nxtChar);
					escapeActive = false;
					break;
				case '0': // take as octal or hex number if escaped
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case 'x': // used to prefix hex numbers if escaped
					if (escapeActive)
					{
						StringBuffer number = new StringBuffer();
						String numbers = "0123456789x";
						int radix = 10;
						if (nxtChar == 0) radix = 8;
						while ((_nxtOfs < _strlen)
								&& (numbers.indexOf(nxtChar) >= 0))
						{
							if (nxtChar == 'x')
								radix = 16;
							else
								number.append(nxtChar);

							if (++_nxtOfs < _strlen)
								nxtChar = _parseLine.charAt(_nxtOfs);
							else
								endOfToken = true;
						}
						_token.append((char) Integer.parseInt(
							number.toString(), radix));
						--_nxtOfs;
						escapeActive = false;
					}
					else
					{
						_token.append(nxtChar);
					}
					break;
				default: // all other characters
					if (nxtChar == quoteChar)
					{
						if (escapeActive)
						{
							_token.append(nxtChar);
							escapeActive = false;
						}
						else if (quoteActive)
						{
							int nofs = _nxtOfs + 1;
							if (nofs < _strlen && _parseLine.charAt(nofs) == quoteChar)
							{
								nxtChar = _parseLine.charAt(_nxtOfs);
								_token.append(quoteChar);
								_nxtOfs = nofs;
							}
							else
							{
								if (_keepQuotes) _token.append(nxtChar);
								quoteActive = false; /* mjr */
							}
						}
						else
						{
							if (_keepQuotes) _token.append(nxtChar);
							quoteActive = true;
						}
					}
					else
					{
						_token.append(nxtChar);
						escapeActive = false;
					}
					break;
				}

				// Move to the next char.
				if (!endOfToken)
				{
					if (++_nxtOfs < _strlen)
						nxtChar = _parseLine.charAt(_nxtOfs);
					else
						endOfToken = true;
				}
			}
		}
		return _token.toString();
	}

	public String[] getTokens()
	{
		Vector<String>v = new Vector<String>();
		while (hasMoreTokens())
		{
			v.addElement(nextToken());
		}
		return v.toArray(new String[v.size()]);
	}

	/**
	 * * Changes the given separator for the given one *
	 * 
	 * @param newsep
	 *            Separator to be set *
	 * @return The given string where the separators were changed * for this new
	 *         one
	 */
	public String replaceSeparator(String newsep)
	{
		String result = null;
		// retrieve the tokens
		String tokens[] = getTokens();
		if (tokens.length > 1)
		{
			// initialize with the first element
			StringBuffer sb = new StringBuffer(tokens[0]);
			// make the conversion replacement of the slashes if there are any
			for (int i = 1; i < tokens.length; i++)
			{
				// add the separator
				sb.append("/");
				sb.append(tokens[i]);
			}
			// set the result string
			result = sb.toString();
		}
		// set the given data as the return value
		else
			result = _parseLine;

		return result;
	}

	static public void main(String args[])
	{
		String s = "1 \\65   \"4 5\" 600 700";
		StringParse p = new StringParse(s, " ");
		System.out.println("Buffer: " + s);
		String tokens[] = p.getTokens();
		for (int i = 0; i < tokens.length; i++)
		{
			System.out.println("Token: " + tokens[i]);
		}
	}
}
