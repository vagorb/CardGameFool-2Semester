Kaardimäng "Fool".
***
Režiimid: Player vs AI või 2 kuni 4 mängijat mängivad üksteise vastu.
Mängu protsess:
Enne mängu alustamisest:
*Esimese sammu teeb inimene, kes istub vasakul eelmise mängu kaotajast või kui see on esimene mäng, mängijat, kes teeb esimest sammu valitakse randomiga.
*class Deck genereeritakse enne mängu alustamisest ning kaarte shuffle randomiga.
*Deckis on 36 kaardi.
*Iga mängija saab 6 kaarti(class Card), aga neid jagatakse ühekaupa alustades võitjast ning pärast vastu kella.
*Pärast võetakse esimest kaardi, mida pannakse Decki juurde ning mis hakkab trumbina.
*Mäng algab:
*Esimene mängija algab rünnakut.
*Ta valib hiirega kaarti, mida tahab panna lauale(class table) ning pressib nuppu "OK".
*Laual saab olema samal ajal ainult üks kaart, mida on vaja tappa.
*Kui tapmine õnnestub, siis rünnalut saab jätkata ning esimene mängija jälle saab panna kaarti lauale või vajutada nuppu "SKIP".
*Kui on rohkem kui 2 mängijat, siis pärast esimest mängijat sammu saavad teised abiründajad vastu kella järjekorras.
*Nad savad kas valida kaardi oma käest sama väärtusega, mis on juba laual või vajutada "SKIP".
*Kui oli lisatud veel ründkaarte, siis samm jätkub kuni iga ründajast vajutab nuppu "SKIP"(ning polnud lisatud uued kaardid lauale) või kuni laual ei hakka 6 tapetud kaardi.
*}nnestunud kaitsmise korral kõik tapetud kaardid lähevad (class PILE)
*Kaitsjal on võimalus vajutada nuppu "TAKE CARDS".
*Kui kaitsja võttab kaarte, siis teised mängijad saavad lisada veel kaarte lauale(kokku kuni 6 kaarte laual, mida oli vaja tappa), mida kaitsja peab võtma.
*Iga mängija, kellel on vähem kui 6 kaarte võtab kaarte deckist kuni mängijal käes on jälle 6 kaarte.
*Esimesena võtab peamine ründaja, pärast teised ründajad ning viimasena kaitsja.
*Käigu saab inimene kaitsja, kui kaitsmine õnnestus või järgmine mängija ebaõnnestunud kaitsmise korral.
*Mäng käib samamoodi kuni Deckis ei lõppe kaardid.
*Pärast seda mängijad ei võta kaardid Deckist ning nende eesmärk on kasutada kõike oma kaarte(nende Handis pole kaarte).
*Esimene mängija, kes kasutas kõike oma kaarte saab võitjaks.
*Mäng läheb edasi ilma selle mängijat kuni jääb ainult 1 mängija kaartidega.
*Ta saabki "FOOLIKS"
SOME IMPORTANT RULES:
Kaardi saab tappa ainult kaart, mis on sama masti ning mille väärtus on suurem, kui ründkaardil.
Ründkaardi, kui ta pole trumbimastist, saab tappa ükskõik mis trumbimastist kaardiga.
Trumbimasti ründkaardi saab tappa ainult kõrgema väärtusega trumbimastist kaart.
Kaartide value (6<7<8<9<10<J<Q<K<A).
AI:
*AI oskab sama asju, mis mängija.
*Tema sammud baseeruvad informatsioonile PILEist, oma mälust,kus on olemas kõik mängija võetud kaardid ebaõnnestunud kaitsmisel ning kaardid, mida mängija ei saanud tappa.
MENU:
*START GAME -> REŽIIMID
*OPTIONS -> MUSIC(ÕPETAJA AGO LOENGUD)
*EKSMATRIKULEERIMINE(EXIT)
SERVER:
*On olemas prototüüp(Peab teha uut normaalset) 

MAYBE IN FUTURE VERSIONS:
HIGHSCORES
MITTE ARVESTUSED
TEAM REŽIIM