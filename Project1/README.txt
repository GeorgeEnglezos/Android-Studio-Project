Που βρίσκονται τα Αρχεία στον φάκελο:

	~Ο φάκελος με τις 2 Classes και τα 3 Activities βρίσκεται στο path MyApplication\app\src\main\java\com\example\myapplication
	~Ο φάκελος με τα layout xml βρίσκεται στο path MyApplication\app\src\main\res\layout
	~Android Manifest MyApplication\app\src\main




Περιληπτικά για την εργασία (Υπάρχουν και σχόλια αναλυτικά στον κώδικα και στα xml):

	~Κλάση User: Περιέχει Constructor, Getters και Setters με τις 4 μεταβλητές που χρειαζόμαστε στην εφαρμογή.(userID,Timestamp,Longitude και Latitude)

	~Κλάση UserDB: Περιέχει τον κώδικα για την δημιουργία του Πίνακα στην Βάση, μια μέθοδο για 
	την εισαγωγή των χρηστών στην βάση, μια μέθοδο για να παίρνω όλα τα timestamp και μια μέθοδο για να 
	βρει και να επιστρέψει έναν χρήστη στην βάση με την χρήση του timestamp και του userID.(Με βάση αυτά που διδαχθήκαμε στις διαλέξεις.)

	~MainActivity: (Πρώτο Activity) έχει 3 TextFields,ένα TextView και δύο Buttons. Τα δύο textfields είναι 
	για δεκαδικούς αριθμούς μόνο(Ρυθμίστηκε στο xml του layout) ,το τρίτο textfield είναι απλό κείμενο
	και με μια μέθοδο μέσα στην κλάση του activity ελέγχεται να μην υπάρχουν κενά μέσα στο TextField.
	Όταν πατιέται το πρώτο κουμπί (έχει eventListener στην onCreate), ελέγχει ότι τα fields δεν είναι κενά,
	βάζει στο TextView το timestamp(Ημερομηνία και ώρα) σε ευανάγνωστη μορφή και φτιάχνει έναν καινούργιο user 
	που αποθηκεύει στην βάση καλώντας την μέθοδο της κλάσης UserDB. Το Δεύτερο κουμπί σε μεταφέρει στο δεύτερο activity.

	~MainActivity2: (Δεύτερο Activity) περιέχει ένα TextField και ένα spinner για το dropdown list. Στην onCreate φτιάχνει 
	μια λίστα για τα timestamps που φορτώνονται με την μέθοδο από την κλάση Userdb.Αν είναι άδεια η λίστα γυρίζει στο πρώτο 
	activity. Αν συμπληρώσει ο χρήστης το userid και επιλέξει και timestamp μπορεί να πατήσει το κουμπί που υπάρχει για να πάει 
	στο τρίτο Activity και να περάσει τις τιμές του timestamp και userid με την χρήση της μεθόδου putExtra().
	
	~MainActivity3: (Τρίτο Activity) μέσα στην onCreate καλεί την μέθοδο της κλάσης UserDB για να ψάξει για το timestamp και userid. 
	Αν βρει τον χρήστη γεμίζει τα texviews που έχει έτοιμα αλλιώς τα αφήνει κενά.