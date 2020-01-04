package net.ankij.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.stree.JacksonJrsTreeCodec;

import net.ankij.type.CardModel;

public class EditPageParserTest {

	@Test
	public void testParseFieldMetadata() throws Exception {
		String value = "[{\n" + "		\"mod\": 1483878603,\n" + "		\"did\": 1483784205367,\n"
				+ "		\"css\": \".card {\\n font-family: arial;\\n font-size: 20px;\\n text-align: center;\\n color: black;\\n background-color: white;\\n}\\n\",\n"
				+ "		\"latexPre\": \"\\\\documentclass[12pt]{article}\\n\\\\special{papersize=3in,5in}\\n\\\\usepackage[utf8]{inputenc}\\n\\\\usepackage{amssymb,amsmath}\\n\\\\pagestyle{empty}\\n\\\\setlength{\\\\parindent}{0in}\\n\\\\begin{document}\\n\",\n"
				+ "		\"usn\": 0,\n" + "		\"name\": \"Eng\",\n" + "		\"tmpls\": [\n" + "			{\n"
				+ "				\"afmt\": \"{{FrontSide}}\\n\\n<hr id=answer>\\n\\ndef: {{Definition}}\\n<p>\\npron: {{Pronunciation}}\\n<p>\\nexp: {{Example}}\\n<p>\\ndt: {{German}}\",\n"
				+ "				\"did\": 1445677794761,\n" + "				\"ord\": 0,\n"
				+ "				\"bafmt\": \"\",\n" + "				\"name\": \"English -> German\",\n"
				+ "				\"qfmt\": \"{{English}}\",\n" + "				\"bqfmt\": \"\"\n" + "			},\n"
				+ "			{\n"
				+ "				\"afmt\": \"{{FrontSide}}\\n\\n<hr id=answer>\\n\\neng: {{English}}\\n<p>\\npron: {{Pronunciation}}\",\n"
				+ "				\"did\": 1483784205367,\n" + "				\"ord\": 1,\n"
				+ "				\"bafmt\": \"\",\n" + "				\"name\": \"German -> English\",\n"
				+ "				\"qfmt\": \"{{German}}\",\n" + "				\"bqfmt\": \"\"\n" + "			}\n"
				+ "		],\n" + "		\"flds\": [\n" + "			{\n" + "				\"font\": \"Arial\",\n"
				+ "				\"media\": [],\n" + "				\"ord\": 0,\n"
				+ "				\"sticky\": false,\n" + "				\"size\": 20,\n"
				+ "				\"rtl\": false,\n" + "				\"name\": \"English\"\n" + "			},\n"
				+ "			{\n" + "				\"font\": \"Arial\",\n" + "				\"media\": [],\n"
				+ "				\"ord\": 1,\n" + "				\"sticky\": false,\n" + "				\"size\": 20,\n"
				+ "				\"rtl\": false,\n" + "				\"name\": \"Definition\"\n" + "			}\n"
				+ "		],\n" + "		\"sortf\": 0,\n" + "		\"req\": [\n" + "			[\n"
				+ "				0,\n" + "				\"all\",\n" + "				[\n" + "					0\n"
				+ "				]\n" + "			],\n" + "			[\n" + "				1,\n"
				+ "				\"all\",\n" + "				[\n" + "					4\n" + "				]\n"
				+ "			]\n" + "		],\n" + "		\"type\": 0,\n"
				+ "		\"latexPost\": \"\\\\end{document}\",\n" + "		\"vers\": [],\n" + "		\"tags\": [],\n"
				+ "		\"id\": \"1483878594047\"\n" + "	}]";

		CardModel card = new EditPageParser(JSON.std.with(new JacksonJrsTreeCodec())).parseCardModels(value).iterator().next();

		assertThat(card.getType()).isEqualTo("Eng");
		assertThat(card.getFields()).containsExactly("English", "Definition");
	}

}
