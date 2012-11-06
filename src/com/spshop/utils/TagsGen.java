package com.spshop.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.terracotta.agent.repkg.de.schlichtherle.io.FileOutputStream;

import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.ProductService;

public class TagsGen {

	private static Logger logger = Logger.getLogger(TagsGen.class);
	private static String INDEX_DIR = "index.dir";
	private static String comma = ",";
	private String indexFile = null;

	private void init() {
		try {
			Properties properties = new Properties();
			properties.load(TagsGen.class
					.getResourceAsStream("/tagConf.properties"));
			indexFile = properties.getProperty(INDEX_DIR);
		} catch (IOException e) {
			logger.fatal(e);
		}
	}

	public void launch() {
		init();
		
		List<String> tags = ServiceFactory.getService(ProductService.class).getTags();
		
		Set<String> tagList = new HashSet<String>();
		for (String tag : tags) {
			if (StringUtils.isNotBlank(tag)) {
				String[] subTags = tag.split(",");
				for (String stag : subTags) {
					if (StringUtils.isNotBlank(stag)) {
						stag = stag.trim();
						tagList.add(stag);
					}
				}
			}
		}

		List<String> sortedTags = new ArrayList<String>(tagList);

		Collections.sort(sortedTags, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});

		StringBuffer a = new StringBuffer("A=");
		StringBuffer b = new StringBuffer("B=");
		StringBuffer c = new StringBuffer("C=");
		StringBuffer d = new StringBuffer("D=");
		StringBuffer e = new StringBuffer("E=");
		StringBuffer f = new StringBuffer("F=");
		StringBuffer g = new StringBuffer("G=");
		StringBuffer h = new StringBuffer("H=");
		StringBuffer i = new StringBuffer("I=");
		StringBuffer j = new StringBuffer("J=");
		StringBuffer k = new StringBuffer("K=");
		StringBuffer l = new StringBuffer("L=");
		StringBuffer m = new StringBuffer("M=");
		StringBuffer n = new StringBuffer("N=");
		StringBuffer o = new StringBuffer("O=");
		StringBuffer p = new StringBuffer("P=");
		StringBuffer q = new StringBuffer("Q=");
		StringBuffer r = new StringBuffer("R=");
		StringBuffer s = new StringBuffer("S=");
		StringBuffer t = new StringBuffer("T=");
		StringBuffer u = new StringBuffer("U=");
		StringBuffer v = new StringBuffer("V=");
		StringBuffer w = new StringBuffer("W=");
		StringBuffer x = new StringBuffer("X=");
		StringBuffer y = new StringBuffer("Y=");
		StringBuffer z = new StringBuffer("Z=");
		StringBuffer number = new StringBuffer("0_9=");
		
		
		//Test data
	/*	Random random = new Random();
		for (int int1 = 0; int1 < 100000; int1++) {
			a.append("A"+random.nextInt()+ comma);
			b.append("B"+random.nextInt()+ comma);
			c.append("C"+random.nextInt()+ comma);
			d.append("D"+random.nextInt()+ comma);
			e.append("E"+random.nextInt()+ comma);
			f.append("F"+random.nextInt()+ comma);
			g.append("G"+random.nextInt()+ comma);
			h.append("H"+random.nextInt()+ comma);
			i.append("I"+random.nextInt()+ comma);
			j.append("J"+random.nextInt()+ comma);
			k.append("K"+random.nextInt()+ comma);
			l.append("L"+random.nextInt()+ comma);
			m.append("M"+random.nextInt()+ comma);
			n.append("N"+random.nextInt()+ comma);
			o.append("O"+random.nextInt()+ comma);
			p.append("P"+random.nextInt()+ comma);
			q.append("Q"+random.nextInt()+ comma);
			r.append("R"+random.nextInt()+ comma);
			s.append("S"+random.nextInt()+ comma);
			t.append("T"+random.nextInt()+ comma);
			u.append("U"+random.nextInt()+ comma);
			v.append("V"+random.nextInt()+ comma);
			w.append("W"+random.nextInt()+ comma);
			x.append("X"+random.nextInt()+ comma);
			y.append("Y"+random.nextInt()+ comma);
			z.append("Z"+random.nextInt()+ comma);
			number.append(random.nextInt(99999999)+ comma);
		}*/
		
		//End test data

		for (String key : sortedTags) {
			if (StringUtils.isNotBlank(key)) {
				key = key.trim().replace("\n", ",").replace("\r", "");
				Character index = key.charAt(0);
				switch (index) {
				case 'A':
					a.append(key + comma);
					continue;
				case 'a':
					a.append(key + comma);
					continue;
				case 'b':
					b.append(key + comma);
					continue;
				case 'B':
					b.append(key + comma);
					continue;
				case 'c':
					c.append(key + comma);
					continue;
				case 'C':
					c.append(key + comma);
					continue;
				case 'd':
					d.append(key + comma);
					continue;
				case 'D':
					d.append(key + comma);
					continue;
				case 'e':
					e.append(key + comma);
					continue;
				case 'E':
					e.append(key + comma);
					continue;
				case 'f':
					f.append(key + comma);
					continue;
				case 'F':
					f.append(key + comma);
					continue;
				case 'g':
					g.append(key + comma);
					continue;
				case 'G':
					g.append(key + comma);
					continue;
				case 'h':
					h.append(key + comma);
					continue;
				case 'H':
					h.append(key + comma);
					continue;
				case 'i':
					i.append(key + comma);
					continue;
				case 'I':
					i.append(key + comma);
					continue;
				case 'j':
					j.append(key + comma);
					continue;
				case 'J':
					j.append(key + comma);
					continue;
				case 'k':
					k.append(key + comma);
					continue;
				case 'K':
					k.append(key + comma);
					continue;
				case 'l':
					l.append(key + comma);
					continue;
				case 'L':
					l.append(key + comma);
					continue;
				case 'm':
					m.append(key + comma);
					continue;
				case 'M':
					m.append(key + comma);
					continue;
				case 'n':
					n.append(key + comma);
					continue;
				case 'N':
					n.append(key + comma);
					continue;
				case 'o':
					o.append(key + comma);
					continue;
				case 'O':
					o.append(key + comma);
					continue;
				case 'p':
					p.append(key + comma);
					continue;
				case 'P':
					p.append(key + comma);
					continue;
				case 'q':
					q.append(key + comma);
					continue;
				case 'Q':
					q.append(key + comma);
					continue;
				case 'r':
					r.append(key + comma);
					continue;
				case 'R':
					r.append(key + comma);
					continue;
				case 's':
					s.append(key + comma);
					continue;
				case 'S':
					s.append(key + comma);
					continue;
				case 't':
					t.append(key + comma);
					continue;
				case 'T':
					t.append(key + comma);
					continue;
				case 'u':
					u.append(key + comma);
					continue;
				case 'U':
					u.append(key + comma);
					continue;
				case 'v':
					v.append(key + comma);
					continue;
				case 'V':
					v.append(key + comma);
					continue;
				case 'w':
					w.append(key + comma);
					continue;
				case 'W':
					w.append(key + comma);
					continue;
				case 'x':
					x.append(key + comma);
					continue;
				case 'X':
					x.append(key + comma);
					continue;
				case 'y':
					y.append(key + comma);
					continue;
				case 'Y':
					y.append(key + comma);
					continue;
				case 'z':
					z.append(key + comma);
					continue;
				case 'Z':
					z.append(key + comma);
					continue;
				case '0':
					number.append(key + comma);
					continue;
				case '1':
					number.append(key + comma);
					continue;
				case '2':
					number.append(key + comma);
					continue;
				case '3':
					number.append(key + comma);
					continue;
				case '4':
					number.append(key + comma);
					continue;
				case '5':
					number.append(key + comma);
					continue;
				case '6':
					number.append(key + comma);
					continue;
				case '7':
					number.append(key + comma);
					continue;
				case '8':
					number.append(key + comma);
					continue;
				case '9':
					number.append(key + comma);
					continue;
				default:
					continue;
				}

			}
		}
			try {
				File file =  new File(indexFile);
				if(!file.exists()){
					file.mkdirs();
					file.createNewFile();
				}
				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(file)));
				writer.write(a + "\n");
				writer.write(b + "\n");
				writer.write(c + "\n");
				writer.write(d + "\n");
				writer.write(e + "\n");
				writer.write(f + "\n");
				writer.write(g + "\n");
				writer.write(h + "\n");
				writer.write(i + "\n");
				writer.write(j + "\n");
				writer.write(k + "\n");
				writer.write(l + "\n");
				writer.write(m + "\n");
				writer.write(n + "\n");
				writer.write(o + "\n");
				writer.write(p + "\n");
				writer.write(q + "\n");
				writer.write(r + "\n");
				writer.write(s + "\n");
				writer.write(t + "\n");
				writer.write(u + "\n");
				writer.write(v + "\n");
				writer.write(w + "\n");
				writer.write(x + "\n");
				writer.write(y + "\n");
				writer.write(z + "\n");
				writer.write(number + "\n");
				writer.flush();
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

	public static void main(String[] args) throws FileNotFoundException {
		new TagsGen().launch();
		// BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new
		// FileOutputStream("C:\\Workspace\\dev\\ShopPro\\war\\WEB-INF\\index\\index.tag")));
		/*
		 * System.out.println((char)65 + "->" + (char)90);
		 * System.out.println((char)48 + "->" + (char)57);
		 */
		// FileOutputStream fileOutputStream =new FileOutputStream(new
		// File("C:\\Workspace\\dev\\ShopPro\\war\\WEB-INF\\index\\index.tag"));
		
		
	}
}
