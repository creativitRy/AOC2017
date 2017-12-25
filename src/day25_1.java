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
		day25_1 solver = new day25_1();
		solver.solve(1, in, out);
		out.close();
	}
	
	static class day25_1
	{
		HashMap<Long, Integer> values = new HashMap<>();
		long cursor = 0L;
		String state;
		
		public void solve(int testNumber, InputReader in, OutputWriter out)
		{
			String begin = in.nextLine();
			begin = begin.split("state ")[1].replaceAll("\\.", "");
			state = begin;
			long end = 0L;
			{
				String line = in.nextLine().substring(36);
				line = line.substring(0, line.indexOf(' '));
				end = Long.parseLong(line);
			}
			
			HashMap<String, ArrayList<day25_1.Instruction>[]> states = new HashMap<>();
			while (true)
			{
				if (in.nextLine() == null)
					break;
				
				String state = in.nextLine().substring(9, 10);
				ArrayList<day25_1.Instruction>[] lol = new ArrayList[2];
				for (int i = 0; i < 2; i++)
				{
					in.nextLine();
					ArrayList<day25_1.Instruction> instructions = new ArrayList<>();
					instructions.add(new Write(Integer.parseInt(in.nextLine().substring(22, 23))));
					String s = in.nextLine();
					if (s.contains("left"))
						instructions.add(new MoveLeft());
					else
						instructions.add(new MoveRight());
					instructions.add(new Continue(in.nextLine().substring(26, 27)));
					lol[i] = instructions;
				}
				states.put(state, lol);
			}
			
			for (long i = 0; i < end; i++)
			{
				Integer val = values.getOrDefault(cursor, 0);
				ArrayList<day25_1.Instruction> instructions;
				if (val == 0)
					instructions = states.get(state)[0];
				else
					instructions = states.get(state)[1];
				
				for (day25_1.Instruction ins : instructions)
				{
					ins.run();
				}
			}
			
			long ans = 0;
			for (Integer integer : values.values())
			{
				if (integer == 1)
					ans++;
			}
			out.println(ans);
			out.flush();
		}
		
		interface Instruction
		{
			void run();
			
		}
		
		class Write implements day25_1.Instruction
		{
			int value;
			
			public Write(int value)
			{
				this.value = value;
			}
			
			
			public void run()
			{
				if (value == 0)
					values.remove(cursor);
				values.put(cursor, value);
			}
			
		}
		
		class MoveLeft implements day25_1.Instruction
		{
			
			public void run()
			{
				cursor--;
			}
			
		}
		
		class MoveRight implements day25_1.Instruction
		{
			
			public void run()
			{
				cursor++;
			}
			
		}
		
		class Continue implements day25_1.Instruction
		{
			String newState;
			
			public Continue(String newState)
			{
				this.newState = newState;
			}
			
			
			public void run()
			{
				state = newState;
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
}

