import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Gahwon
 */
public class Main
{
	public static void main(String[] args)
	{
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		OutputWriter out = new OutputWriter(outputStream);
		day23_1 solver = new day23_1();
		solver.solve(1, in, out);
		out.close();
	}
	
	static class day23_1
	{
		long ans = 0;
		
		public void solve(int testNumber, InputReader in, OutputWriter out)
		{
			ArrayList<day23_1.Instruction> list = new ArrayList<>();
			while (true)
			{
				String line = in.nextLine();
				if (line == null || line.isEmpty())
					break;
				
				if (line.startsWith("set"))
					list.add(new Set(line));
				else if (line.startsWith("sub"))
					list.add(new Sub(line));
				else if (line.startsWith("mul"))
					list.add(new Mul(line));
				else
					list.add(new Jnz(line));
			}
			
			int i = 0;
			HashMap<Character, Long> map = new HashMap<>();
			while (i < list.size())
			{
				day23_1.Instruction instruction = list.get(i);
				i += instruction.run(map);
			}
			
			out.println(ans);
			out.flush();
		}
		
		private static day23_1.Value get(String parse)
		{
			if (parse.matches("-?\\d+"))
				return new day23_1.Literal(parse);
			else
				return new day23_1.Variable(parse);
		}
		
		interface Value
		{
			long get(HashMap<Character, Long> map);
			
		}
		
		static class Literal implements day23_1.Value
		{
			private long value;
			
			public Literal(String value)
			{
				this.value = Long.parseLong(value);
			}
			
			
			public long get(HashMap<Character, Long> map)
			{
				return value;
			}
			
		}
		
		static class Variable implements day23_1.Value
		{
			private char var;
			
			public Variable(String var)
			{
				this.var = var.charAt(0);
			}
			
			
			public long get(HashMap<Character, Long> map)
			{
				Long temp = map.get(var);
				return temp == null ? 0 : temp;
			}
			
		}
		
		interface Instruction
		{
			int run(HashMap<Character, Long> map);
			
		}
		
		class Set implements day23_1.Instruction
		{
			private char var;
			private day23_1.Value val;
			
			public Set(String line)
			{
				var = line.charAt(4);
				val = get(line.substring(6).trim());
			}
			
			
			public int run(HashMap<Character, Long> map)
			{
				map.put(var, val.get(map));
				return 1;
			}
			
		}
		
		class Sub implements day23_1.Instruction
		{
			private char var;
			private day23_1.Value prev;
			private day23_1.Value val;
			
			public Sub(String line)
			{
				var = line.charAt(4);
				prev = get("" + var);
				val = get(line.substring(6).trim());
			}
			
			
			public int run(HashMap<Character, Long> map)
			{
				map.put(var, prev.get(map) - val.get(map));
				return 1;
			}
			
		}
		
		class Mul implements day23_1.Instruction
		{
			private char var;
			private day23_1.Value prev;
			private day23_1.Value val;
			
			public Mul(String line)
			{
				var = line.charAt(4);
				prev = get("" + var);
				val = get(line.substring(6).trim());
			}
			
			
			public int run(HashMap<Character, Long> map)
			{
				map.put(var, prev.get(map) * val.get(map));
				ans++;
				return 1;
			}
			
		}
		
		class Jnz implements day23_1.Instruction
		{
			private char var;
			private day23_1.Value prev;
			private day23_1.Value val;
			
			public Jnz(String line)
			{
				var = line.charAt(4);
				prev = get("" + var);
				val = get(line.substring(6).trim());
			}
			
			
			public int run(HashMap<Character, Long> map)
			{
				if (prev.get(map) != 0)
					return (int) val.get(map);
				return 1;
			}
			
		}
		
	}
	
	static class InputReader
	{
		public BufferedReader reader;
		public StringTokenizer tokenizer;
		
		public InputReader(String fileName) throws FileNotFoundException
		{
			this(new FileReader(new File(fileName)));
		}
		
		public InputReader(InputStream in)
		{
			this(new InputStreamReader(in));
		}
		
		public InputReader(Reader reader)
		{
			this.reader = new BufferedReader(reader, 32768);
			tokenizer = null;
		}
		
		public String nextLine()
		{
			try
			{
				String str = reader.readLine();
				tokenizer = null;
				return str;
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		
	}
	
	static class OutputWriter extends PrintWriter
	{
		public OutputWriter(OutputStream out)
		{
			this(new OutputStreamWriter(out));
		}
		
		public OutputWriter(OutputStream out, boolean autoFlush)
		{
			this(new OutputStreamWriter(out), autoFlush);
		}
		
		public OutputWriter(String fileName) throws IOException
		{
			this(new FileWriter(fileName));
		}
		
		@Deprecated
		public OutputWriter(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException
		{
			super(fileName, csn);
		}
		
		public OutputWriter(File file) throws IOException
		{
			this(new FileWriter(file));
		}
		
		@Deprecated
		public OutputWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException
		{
			super(file, csn);
		}
		
		public OutputWriter(Writer out)
		{
			this(out, false);
		}
		
		public OutputWriter(Writer out, boolean autoFlush)
		{
			super(new BufferedWriter(out), autoFlush);
		}
		
		
		public void close()
		{
			super.close();
		}
		
	}
}

