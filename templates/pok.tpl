<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <!-- Portions taken from pcgen580/outputsheets/d20/fantasy/htmlxml/csheet_fantasy_std.htm -->
  <head>
    <meta http-equiv="content-type" content="text-html; charset=utf-8" />
    <title>|NAME| Shadowrun character sheet</title>

    <style TYPE="text/css">
	.font4 {  font-family: Arial; font-size:4pt; }
	.font5 {  font-family: Arial; font-size:5pt; }
	.font6 {  font-family: Arial; font-size:6pt; }
	.font7 {  font-family: Arial; font-size:7pt; }
	.font8 {  font-family: Arial; font-size:8pt; }
	.font9 {  font-family: Arial; font-size:9pt; }
	.h { font-family: Arial; font-size:8pt; vertical-align: bottom; }
	.ab { font-family: Arial; font-size:10pt; text-align:center; color:white; background:black; }
	.abb { font-family: Arial; font-size:10pt; text-align:center; font-weight:bold; border: 1px solid black;}
	.abt { font-family: Arial; font-size:10pt; text-align:center; font-weight:bold; border: 5px solid lightgray;}
	.sptab { font-family: Arial; vertical-align:top; text-align:center;font-size:8pt;}
	.sptab1 { font-family: Arial; vertical-align:top; text-align:left;font-size:8pt;}
	.sptab2 { font-family: Arial; vertical-align:top; text-align:right;font-size:8pt;}
	.notetab { font-family: Arial; vertical-align:top;border: 1px solid black; text-align:left;font-size:8pt;}
	.skl { font-family: Arial; font-size: 9pt; font-weight:bold; text-align:center; vertical-align:middle; border: 4px solid black;}
	.spname { font-family: Arial; vertical-align:top; text-align:left;font-size:8pt;}
	.sptop { font-family: Arial; vertical-align:top;border: 1px solid black; text-align:center;font-size:8pt; color:white;}
	.splevel {  font-family: Arial; background:black; text-align:center;font-size:9pt; color:white;}
	.sphead {  font-family: Arial; background:black; text-align:center;font-size:12pt; color:white;}
	.notehead {  font-family: Arial; background:black; text-align:center;font-size:12pt; color:white;}
	.biodata { font-family: Arial; vertical-align:bottom; text-align:left;font-size:9pt;}
		.topline {
		border-top-width:1px;
		border-top: 1pt solid black;
		font-family: Arial;
		font-size:6pt;
	}
	.border {
		border: 1px solid black;
	}
	.border4 {
		border: 1px solid black;
		font-size:4pt;
	}
	.border7 {
		border: 1px solid black;
		font-size:7pt;
	}
	.border8 {
		border: 1px solid black;
		font-size:8pt;
	}
	.border9 {
		border: 1px solid black;
		font-size:9pt;
	}
	.border10 {
		border: 1px solid black;
		font-size:10pt;
	}

	.tempborder {
		border: 5px solid lightgray;
	}
	.borderbottom {
		border-bottom-width:1px;
		border-bottom: 1pt solid black;
	}
	.borderbottom8 {
		border-bottom-width:1px;
		border-bottom: 1pt solid black;
		font-size:8pt;
	}
    </style>
  </head>
  <body bgcolor="white">
	<center><font size="-1">Created using SRGen |EXPORT.VERSION| on |EXPORT.DATE|</font></center><br>
	<!-- START Top Character Data -->
	<table cellpadding="0" cellspacing="4" border="0" width="100%" summary="Character Info">
	  <tr>
	   <td colspan="2" class="h">|NAME.REAL|</td>
	   <td colspan="2" class="h">|NAME.CHAR|</td>
	   <td colspan="2" class="h">|NAME.PLAYER|</td>
	   <td colspan="1" class="h">|NAME.STREET|</td>
	   <td rowspan="6" align="center" width="1%" class="border"><font style="font-size:10pt"><A HREF="#Bio"><IMG SRC="|PORTRAIT|" height="100" ALT="Click for Bio" BORDER="0"></A><br /></font></td>
	 </tr>
	 <tr>
       <td colspan="2" class="topline">REAL NAME</td>
       <td colspan="2" class="topline">CHARACTER NAME</td>
       <td colspan="2" class="topline">PLAYER</td>
       <td colspan="1" class="topline">STREET NAME</td>
	 </tr>

  	 <tr>
       <td colspan="1" class="h">|NAME.ARCHETYPE|</td>
       <td colspan="1" class="h">|VITALS.RACE|</td>
       <td colspan="1" class="h">|VITALS.BIRTHDATE|</td>
       <td class="h">|VITALS.BIRTHPLACE|</td>
       <td class="h">|VITALS.HEIGHT|</td>
       <td class="h">|VITALS.WEIGHT|</td>
       <td colspan="1" class="h">|VISION|</td>
	</tr>
    <tr>
       <td colspan="1" class="topline">ARCHETYPE</td>
       <td colspan="1" class="topline">RACE</td>
       <td colspan="1" class="topline">BIRTHDATE</td>
       <td class="topline">BIRTHPLACE</td>
       <td class="topline">HEIGHT</td>
       <td class="topline">WEIGHT</td>
       <td colspan="1" class="topline">VISION</td>
    </tr>

 	<tr>
	   <td class="h">|KARMA.TOTAL|</td>
	   <td class="h">|KARMA.FREE|</td>
	   <td class="h">|VITALS.AGE|</td>
	   <td class="h">|VITALS.SEX|</td>
	   <td class="h">|VITALS.EYES|</td>
	   <td colspan="2" class="h">|VITALS.HAIR|</td>
    </tr>
    <tr>
      <td class="topline">TOTAL KARMA</td>
      <td class="topline">FREE KARMA</td>
      <td class="topline">AGE</td>
      <td class="topline">GENDER</td>
      <td class="topline">EYES</td>
      <td colspan="2" class="topline">HAIR</td>
    </tr>
 
   </table>
<!-- STOP Top Character Data -->

<!-- START Abilities table -->
      <table summary="Stat Block">
        <tr>
          <td align="center" width="20%" class="font4">ATTR<br />NAME</td>
          <td align="center" width="20%" class="font4">ATTR<br />BASE</td>
          <td align="center" width="20%" class="font4">ATTR<br />SCORE</td>
          <td align="center" width="20%" class="font4">TEMP<br />SCORE</td>
        </tr>
|FOR,%attr,0,5,1,1|
        <tr>
          <td class="ab"><b>|ATTR.%attr.SHORTNAME|</b><font style="font-size:5pt"><br />|ATTR.%attr.LONGNAME|</font></td>
          <td class="abb">|ATTR.%attr.BASE|</td>
          <td class="abb">|ATTR.%attr|</td>
          <td class="abt"> </td>
        </tr>
|ENDFOR|
        </table> 
<!-- STOP Abilities Table -->

<!-- START Pools Table -->
      <table summary="Pools Block">
        <tr>
          <td class="ab"><b>Karma</b></td>
          <td class="abb">|POOLS.KARMA|</td>
          <td class="ab"><b>Spell</b></td>
          <td class="abb">|POOLS.SPELL|</td>
        </tr>
        <tr>
          <td class="ab"><b>Combat</b></td>
          <td class="abb">|POOLS.COMBAT|/|POOLS.COMBAT_MOD|</td>
          <td class="ab"><b>Task</b></td>
          <td class="abb">|POOLS.TASK|</td>
        </tr>
        <tr>
          <td class="ab"><b>Control</b></td>
          <td class="abb">|POOLS.CONTROL|</td>
          <td class="ab"><b>Astral</b></td>
          <td class="abb">|POOLS.ASTRAL|</td>
        </tr>
        <tr>
          <td class="ab"><b>Hacking</b></td>
          <td class="abb">|POOLS.HACKING|</td>
          <td colspan="2"></td>
        </tr>
      </table>

<table cellpadding="0" cellspacing="0" border="0" width="100%" summary="Edges and Flaws Table">
    <tr>
     <td height="30" bgcolor="black" align="center"><font style="font-size: 9pt" color="white"><b>EDGES &amp; FLAWS</b></font></td>
     <td height="30" bgcolor="black" align="center"></td>
    </tr>
    <tr>
     <td align="center" width="60%" class="border4">E/F NAME</td>
     <td align="center" width="40%" class="border4">COST</td>
    </tr>
|FOR,%index,0,EDGE.COUNT,1,0|
|OIF(EVEN:%index,<tr bgcolor="#DDDDDD">,<tr bgcolor="white">)|
  <td align="left" class="font8">|EDGE.%index.NAME|</td>
  <td align="left" class="borderbottom8" valign="bottom">|EDGE.%index.COST|</td>
</tr>
|ENDFOR|
</table>


<!-- START Skills Table -->
   <table cellpadding="0" cellspacing="0" border="0" width="100%" summary="Skills Table">
    <tr>
     <td height="30" bgcolor="black" align="center"><font style="font-size: 9pt" color="white"><b>SKILLS</b></font></td>
     <td height="30" bgcolor="black" align="center"></td>
    </tr>
    <tr>
     <td align="center" width="60%" class="border4">SKILL NAME</td>
     <td align="center" width="40%" class="border4">MODIFIER</td>
    </tr>
    XXX: |SKILL.COUNT|<br>
|FOR,%skill,0,SKILL.COUNT,1,0|
|OIF(EVEN:%skill,<tr bgcolor="#DDDDDD">,<tr bgcolor="white">)|
     <td align="left" class="font8">&nbsp;&nbsp;|SKILL.%skill.NAME|</td>
     <td align="left" class="borderbottom8" valign="bottom">|SKILL.%skill.ST|</td>
    </tr>
|ENDFOR|
   </table>
<!-- STOP Skills Table -->

<!-- START Vehicles Table -->
   <table cellpadding="0" cellspacing="0" border="0" width="100%" summary="Vehicles Table">
    <tr>
     <td height="30" bgcolor="black" align="center"><font style="font-size: 9pt" color="white"><b>VEHICLES</b></font></td>
     <td height="30" bgcolor="black" align="center"></td>
    </tr>
    <tr>
     <td align="center" width="60%" class="border4">VEHICLE NAME</td>
     <td align="center" width="40%" class="border4">MODIFIER</td>
    </tr>
|FOR,%index,0,VEHICLE.COUNT,1,0|
|OIF(EVEN:%index,<tr bgcolor="#DDDDDD">,<tr bgcolor="white">)|
     <td align="left" class="font8">&nbsp;&nbsp;|VEHICLE.%index.NAME|</td>
     <td align="left" class="borderbottom8" valign="bottom">|VEHICLE.%index.ST|</td>
    </tr>
|ENDFOR|
   </table>
<!-- STOP Vehicles Table -->

<!-- START Cyberware Table -->
   <table cellpadding="0" cellspacing="0" border="0" width="100%" summary="Cyberware Table">
    <tr>
     <td height="30" bgcolor="black" align="center"><font style="font-size: 9pt" color="white"><b>CYBERWARE</b></font></td>
     <td height="30" bgcolor="black" align="center"></td>
    </tr>
    <tr>
     <td align="center" width="60%" class="border4">CYBERWARE NAME</td>
     <td align="center" width="40%" class="border4">NOTES</td>
    </tr>
|FOR,%index,0,CYBERWARE.COUNT,1,0|
|OIF(EVEN:%index,<tr bgcolor="#DDDDDD">,<tr bgcolor="white">)|
     <td align="left" class="font8">&nbsp;&nbsp;|CYBERWARE.%index.NAME|</td>
     <td align="left" class="borderbottom8" valign="bottom">|CYBERWARE.%index.NOTES|</td>
    </tr>
|ENDFOR|
   </table>
<!-- STOP Cyberware Table -->

<!-- START Contacts Table -->
   <table cellpadding="0" cellspacing="0" border="0" width="100%" summary="Contacts Table">
    <tr>
     <td height="30" bgcolor="black" align="center"><font style="font-size: 9pt" color="white"><b>CONTACTS</b></font></td>
     <td height="30" bgcolor="black" align="center"></td>
     <td height="30" bgcolor="black" align="center"></td>
    </tr>
    <tr>
     <td align="center" width="40%" class="border4">NAME</td>
     <td align="center" width="30%" class="border4">RACE</td>
     <td align="center" width="30%" class="border4">OCCUPATION</td>
    </tr>
|FOR,%index,0,CONTACT.COUNT,1,0|
|OIF(EVEN:%index,<tr bgcolor="#DDDDDD">,<tr bgcolor="white">)|
     <td align="left" class="font8">&nbsp;&nbsp;|CONTACT.%index.NAME|</td>
     <td align="left" class="borderbottom8" valign="bottom">|CONTACT.%index.RACE|</td>
     <td align="left" class="borderbottom8" valign="bottom">|CONTACT.%index.OCCUPATION|</td>
    </tr>
|ENDFOR|
   </table>
<!-- STOP Contacts Table -->

<!-- START Decks Table -->
   <table cellpadding="0" cellspacing="0" border="0" width="100%" summary="Decks Table">
    <tr>
     <td height="30" bgcolor="black" align="center"><font style="font-size: 9pt" color="white"><b>DECKS</b></font></td>
     <td height="30" bgcolor="black" align="center"></td>
    </tr>
    <tr>
     <td align="center" width="60%" class="border4">NAME</td>
     <td align="center" width="40%" class="border4">PERSONA</td>
    </tr>
|FOR,%index,0,DECK.COUNT,1,0|
|OIF(EVEN:%index,<tr bgcolor="#DDDDDD">,<tr bgcolor="white">)|
     <td align="left" class="font8">&nbsp;&nbsp;|DECK.%index.NAME|</td>
     <td align="left" class="borderbottom8" valign="bottom">|DECK.%index.PERSONA|</td>
    </tr>
|ENDFOR|
   </table>
<!-- STOP Decks Table -->

<!-- START Damage Table -->
   <table cellpadding="0" cellspacing="0" border="1" width="50%" summary="Condition Table">
    <tr>
     <td height="30" bgcolor="black" align="center" colspan=11><font style="font-size: 9pt" color="white"><b>CONDITION</b></font></td>
    </tr>
    <tr>
     <td height="30" bgcolor="black" align="center" colspan=1><font style="font-size: 8pt" color="white">&nbsp;</font></td>
     <td height="30" bgcolor="black" align="center" colspan=2><font style="font-size: 8pt" color="white">TN +1<br>Init -1</font></td>
     <td height="30" bgcolor="black" align="center" colspan=3><font style="font-size: 8pt" color="white">TN +2<br>Init -2</font></td>
     <td height="30" bgcolor="black" align="center" colspan=4><font style="font-size: 8pt" color="white">TN +3<br>Init -3</font></td>
     <td height="30" bgcolor="black" align="center" colspan=1><font style="font-size: 8pt" color="white">&nbsp;</font></td>
     <td height="30" bgcolor="black" align="center" colspan=6><font style="font-size: 8pt" color="white">Overdamage</font></td>
    </tr>
    <tr>
     <td height="20" align="center"><font style="font-size: 9pt"><b>Stun:</b></font></td>
     <td width="20" align="center"><font style="font-size: 7pt">L</font></td>
     <td width="20" align="center"></td>
     <td width="20" align="center"><font style="font-size: 7pt">M</font></td>
     <td width="20" align="center"></td>
     <td width="20" align="center"></td>
     <td width="20" align="center"><font style="font-size: 7pt">S</font></td>
     <td width="20" align="center"></td>
     <td width="20" align="center"></td>
     <td width="20" align="center"></td>
     <td width="20" align="center">D</td>
     <td align="center" colspan=6>Overflows into physical</td>
    </tr>
    <tr>
     <td height="20" align="center"><font style="font-size: 9pt"><b>Physical:</b></font></td>
     <td width="20" align="center">L</td>
     <td width="20" align="center"></td>
     <td width="20" align="center">M</td>
     <td width="20" align="center"></td>
     <td width="20" align="center"></td>
     <td width="20" align="center">S</td>
     <td width="20" align="center"></td>
     <td width="20" align="center"></td>
     <td width="20" align="center"></td>
     <td width="20" align="center">D</td>
     <td width="20" align="center"></td>
     <td width="20" align="center"></td>
     <td width="20" align="center"></td>
     <td width="20" align="center"></td>
     <td width="20" align="center"></td>
     <td width="20" align="center"></td>
    </tr>
   </table>
<!-- STOP Damage Table -->

  </body>
</html>