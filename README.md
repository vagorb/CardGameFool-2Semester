Kaardimäng "Fool".
***
* MÄNGU PROTSESS:
* Enne mängu alustamisest:
* Esimese sammu teeb inimene, kellel on kõige väiksem Trump kaart.
* class Deck genereeritakse enne mängu alustamisest Serveril.
* Deckis on 36 kaardi.
* Iga mängija saab 6 kaarti(class Card).
* Pärast võetakse esimest kaardi, mida pannakse Decki juurde ning mis hakkab trumbina.
* Mäng algab:
* Esimene mängija algab rünnakut.
* Ta valib hiirega kaarti, mida tahab panna lauale(class table) vajutab kaardi peale ning kohale.kuhu on vaja seda panna laua peal.
* Laual saab olema samal ajal ainult üks kaart, mida on vaja tappa.
* Kui tapmine õnnestub, siis rünnakut saab jätkata ning esimene mängija jälle saab panna kaarti lauale või vajutada nuppu "SKIP".
* Samm jätkub kuni ründaja vajutab nuppu "SKIP" või kuni laual ei hakka 6 tapetud kaardi.
* Õnnestunud kaitsmise korral kõik tapetud kaardid lähevad (class PILE)
* Kaitsjal on võimalus vajutada nuppu "TAKE".
* Kui kaitsja võttab kaarte, mida ta pidi tappa ning oma kaardid, millega ta kaitses.
* Ründajal on võimalus veel kaarte lisada(kui laual on juba kaardid sama väärtusega), kui kaitsja vajutab nuppu "TAKE".(maksimaalselt 6 ründkaarti laual)
* Iga mängija, kellel on vähem kui 6 kaarte võtab kaarte deckist kuni mängijal käes on jälle 6 kaarte.
* Esimesena võtab kaarte ründaja, viimasena kaitsja.
* Käigu saab inimene kaitsja, kui kaitsmine õnnestus või jälle ründaja.
* Mäng käib samamoodi kuni Deckis ei lõppe kaardid.
* Pärast seda mängijad ei võta kaardid Deckist ning nende eesmärk on kasutada kõike oma kaarte(et nende käes polnud kaarte).
* Esimene mängija, kes kasutas kõike oma kaarte saab võitjaks.
* Mängija, kes ei kasutanud kõike oma kaarte enne teist mängijat saabki "FOOLIKS"
* SOME IMPORTANT RULES:
* Kaardi saab tappa ainult kaart, mis on sama masti ning mille väärtus on suurem, kui ründkaardil.
* Ründkaardi, kui ta pole trumbimastist, saab tappa ükskõik mis trumbimastist kaardiga.
* Trumbimasti ründkaardi saab tappa ainult kõrgema väärtusega trumbimastist kaart.
* Kaartide value (6<7<8<9<10<J<Q<K<A).
* MENU:
* START GAME -> REŽIIMID
*  -> Ekraaniresolutsioon
* EXIT -> Sulgeda mängu
***
KASUTATUD TEHNOLOOGIAD:
* Kood on kirjutatud Java keeles.
* Server on tehtud kasutades Netty tehnoloogiaid(Java keeles).
* Kaardide piltide jaoks oli kasutatud CSS.
* Visuaalne osa oli tehtud JavaFX-iga.
***
ARHITEKTUURILINE JOONIS:
![Text](OurGameDiagrammFinalCropped.png)
***
SERVER:
* Mängu alguses Serverile saadakse sõnumit sellest
* Server loob Decki mängu jaoks, kaardid millest hakkavad mängijad kasutama selle mängu jaoks. 
Decki salvestatakse ainult serveri peal.
* Server saadab igale mängijale 6 kaardi ning milline kaardt on trumbi kaart.
* Mängijad saadavad oma tehtud käigud(Kaardid) ning oma käe suurust.
* Server saadab teisele mängijale kaarti, mida pani esimene mängija ja vastupidi.
* Mängijad saavad veel saada kaks sõnumit serverile kas "SKIP", mis tähendab, et käik on lõpetatud või "TAKE", mis tähendab, et mängija võttis kaarte.
* Edastamiseks on kasutatud TCP protokoll. 
***
AI:
* AI asub serveri peal.
* AI on nagu tavaline mängija, mis paneb kaardid lauale, aga tal on oma loogika.
* AI TEEB OMA KÄIGUD:
* vastavalt situatsioonist laualt, 
* vastavalt kaartidest, mis on juba väljunud mängust, 
* vastavalt kaartide omadustest(Nt, kui tal on mitu sama väärtusega kaarti)
* mängu lõpus ta saab teada inimese kaartid tänu sellele, et ta mäletab kõike kaarte mis on väljunud
***
Mängu installeerimise juhend:
IN FUTURE!
