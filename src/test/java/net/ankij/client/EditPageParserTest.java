package net.ankij.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.stree.JacksonJrsTreeCodec;

import net.ankij.type.CardModel;
import net.ankij.type.Deck;

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

		CardModel card = new EditPageParser(JSON.std.with(new JacksonJrsTreeCodec())).parseCardModels(value).get("Eng")
				.get();

		assertThat(card.getName()).isEqualTo("Eng");
		assertThat(card.getId()).isEqualTo("1483878594047");
		assertThat(card.getFields()).containsExactly("English", "Definition");
	}

	@Test
	public void testParseDeck() throws Exception {
		String value = "{\r\n" + "	\"1445677794761\": {\r\n" + "		\"id\": 1445677794761,\r\n"
				+ "		\"mid\": \"1483783358744\",\r\n" + "		\"timeToday\": [\r\n" + "			1533,\r\n"
				+ "			0\r\n" + "		],\r\n" + "		\"collapsed\": false,\r\n" + "		\"desc\": \"\",\r\n"
				+ "		\"mod\": 1578006111,\r\n" + "		\"lrnToday\": [\r\n" + "			1533,\r\n"
				+ "			0\r\n" + "		],\r\n" + "		\"newToday\": [\r\n" + "			1533,\r\n"
				+ "			0\r\n" + "		],\r\n" + "		\"extendNew\": 10,\r\n" + "		\"dyn\": 0,\r\n"
				+ "		\"usn\": 1523,\r\n" + "		\"extendRev\": 50,\r\n" + "		\"conf\": 1,\r\n"
				+ "		\"browserCollapsed\": true,\r\n" + "		\"name\": \"English -> German\",\r\n"
				+ "		\"revToday\": [\r\n" + "			1533,\r\n" + "			0\r\n" + "		]\r\n" + "	}}";

		Deck deck = new EditPageParser(JSON.std.with(new JacksonJrsTreeCodec())).parseDecks(value)
				.get("English -> German").get();

		assertThat(deck.getName()).isEqualTo("English -> German");
		assertThat(deck.getDefaultMid()).isEqualTo("1483783358744");

	}

	@Test
	public void testParseMultipleDecks() throws Exception {
		String value = "{\"1445677794761\": {\"id\": 1445677794761, \"mid\": \"1483783358744\", \"timeToday\": [1533, 0], \"collapsed\": false, \"desc\": \"\", \"mod\": 1578006111, \"lrnToday\": [1533, 0], \"newToday\": [1533, 0], \"extendNew\": 10, \"dyn\": 0, \"usn\": 1523, \"extendRev\": 50, \"conf\": 1, \"browserCollapsed\": true, \"name\": \"English -> German\", \"revToday\": [1533, 0]}, \"1483784205367\": {\"id\": 1483784205367, \"mid\": \"1483783358744\", \"timeToday\": [1533, 0], \"newToday\": [1533, 0], \"desc\": \"\", \"mod\": 1578004602, \"lrnToday\": [1533, 0], \"collapsed\": false, \"extendNew\": 10, \"dyn\": 0, \"usn\": 1523, \"extendRev\": 50, \"conf\": 1, \"browserCollapsed\": true, \"name\": \"German -> English\", \"revToday\": [1533, 0]}, \"1483878755546\": {\"id\": 1483878755546, \"mid\": \"1445677744637\", \"timeToday\": [1533, 0], \"collapsed\": false, \"desc\": \"\", \"newToday\": [1533, 0], \"lrnToday\": [1533, 0], \"mod\": 1578004611, \"extendNew\": 10, \"dyn\": 0, \"usn\": 1523, \"extendRev\": 50, \"conf\": 1, \"browserCollapsed\": true, \"name\": \"English Phrases\", \"revToday\": [1533, 0]}, \"1524250842964\": {\"name\": \"\\u00d6sterreichisch\", \"extendRev\": 50, \"usn\": 1392, \"collapsed\": false, \"browserCollapsed\": true, \"newToday\": [1533, 0], \"lrnToday\": [1533, 0], \"dyn\": 0, \"extendNew\": 10, \"conf\": 1, \"revToday\": [1533, 0], \"timeToday\": [1533, 0], \"id\": 1524250842964, \"mod\": 1569775784, \"desc\": \"\"}, \"1452878728503\": {\"id\": 1452878728503, \"mid\": \"1445677744637\", \"timeToday\": [1533, 0], \"collapsed\": false, \"mod\": 1577882723, \"desc\": \"\", \"lrnToday\": [1533, 0], \"newToday\": [1533, 0], \"extendNew\": 10, \"dyn\": 0, \"usn\": 1522, \"extendRev\": 50, \"conf\": 1, \"browserCollapsed\": true, \"name\": \"Pronuncation\", \"revToday\": [1533, 0]}, \"1\": {\"desc\": \"\", \"mod\": 1447358454, \"extendRev\": 50, \"newToday\": [1533, 0], \"usn\": 0, \"revToday\": [1533, 0], \"extendNew\": 10, \"timeToday\": [1533, 0], \"browserCollapsed\": true, \"lrnToday\": [1533, 0], \"collapsed\": false, \"name\": \"Default\", \"conf\": 1, \"dyn\": 0, \"id\": 1}, \"1477730652331\": {\"desc\": \"\", \"mod\": 1483791836, \"extendRev\": 50, \"newToday\": [1533, 0], \"usn\": 0, \"revToday\": [1533, 0], \"extendNew\": 10, \"timeToday\": [1533, 0], \"browserCollapsed\": true, \"lrnToday\": [1533, 0], \"collapsed\": false, \"name\": \"Hebrew\", \"conf\": 1, \"mid\": \"1483783358744\", \"dyn\": 0, \"id\": 1477730652331}}";

		Deck deck = new EditPageParser(JSON.std.with(new JacksonJrsTreeCodec())).parseDecks(value)
				.get("English -> German").get();

		assertThat(deck.getName()).isEqualTo("English -> German");
		assertThat(deck.getDefaultMid()).isEqualTo("1483783358744");
	}

}
