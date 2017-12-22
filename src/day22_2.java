import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.Writer;
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
		day22_1 solver = new day22_1();
		solver.solve(1, in, out);
		out.close();
	}
	
	static class day22_1
	{
		public void solve(int testNumber, InputReader in, OutputWriter out)
		{
			ArrayList<String> list = new ArrayList<>();
			while (true)
			{
				String line = in.nextLine();
				if (line == null || line.isEmpty())
					break;
				list.add(line);
			}
			HashMap<Coord, Integer> infected = new HashMap<>();
			int middle = list.size() / 2;
			for (int i = 0; i < list.size(); i++)
			{
				char[] c = list.get(i).toCharArray();
				for (int j = 0; j < c.length; j++)
				{
					if (c[j] == '#')
					{
						Coord cc = new Coord(j - middle, i - middle);
						infected.put(cc, 2);
					}
				}
			}
			
			long ans = 0;
			Coord coord = new Coord();
			Direction dir = Direction.N;
			
			for (int burst = 0; burst < 10000000; burst++)
			{
				int temp = infected.getOrDefault(coord, 0);
				if (temp == 0)
				{
					infected.put(coord, 1);
					dir = dir.rotateLeft();
				}
				else if (temp == 1)
				{
					infected.put(coord, 2);
					ans++;
				}
				else if (temp == 2)
				{
					infected.put(coord, 3);
					dir = dir.rotateRight();
				}
				else
				{
					infected.remove(coord);
					dir = dir.flip();
				}
				coord = coord.add(dir.getCoord());
			}
			
			out.println(ans);
			
			out.flush();
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
	
	static class Coord
	{
		public int x;
		public int y;
		
		public Coord()
		{
			this(0, 0);
		}
		
		public Coord(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public Coord(Coord copy)
		{
			this(copy.x, copy.y);
		}
		
		public Coord add(Coord coord)
		{
			return new Coord(x + coord.x, y + coord.y);
		}
		
		
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			
			Coord coord = (Coord) o;
			
			if (x != coord.x) return false;
			return y == coord.y;
		}
		
		
		public int hashCode()
		{
			int result = x;
			result = 31 * result + y;
			return result;
		}
		
		
		public String toString()
		{
			return "Coord{" +
				"x=" + x +
				", y=" + y +
				'}';
		}
		
	}
	
	static enum Direction
	{
		E(1, 0),
		N(0, -1),
		W(-1, 0),
		S(0, 1),;
		int x;
		int y;
		
		Direction(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public Direction rotateLeft()
		{
			switch (this)
			{
				case E:
					return N;
				case N:
					return W;
				case W:
					return S;
				case S:
					return E;
			}
			throw new UnsupportedOperationException("Unknown direction");
		}
		
		public Direction rotateRight()
		{
			switch (this)
			{
				case E:
					return S;
				case N:
					return E;
				case W:
					return N;
				case S:
					return W;
			}
			throw new UnsupportedOperationException("Unknown direction");
		}
		
		public Direction flip()
		{
			switch (this)
			{
				case E:
					return W;
				case N:
					return S;
				case W:
					return E;
				case S:
					return N;
			}
			throw new UnsupportedOperationException("Unknown direction");
		}
		
		public Coord getCoord()
		{
			return new Coord(x, y);
		}
		
	}
}

