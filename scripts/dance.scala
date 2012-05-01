
// Add the renamers you want to renamers : ListBuffer[SimpleFileRenamer]
// Use optional args : List[String]


// Here args is a list of capitalize exceptions with specific case (e.g. ATB DJ tyDi)
val exs = args
  
renamers += new RegexRenamer("_", " ")							// Replaces underscores
renamers += new RegexRenamer("""^\d+\s*-\s*""", "")				// Removes track number
renamers += new RegexRenamer("""\s*-\s*""", " - ")				// Puts spaces around '-'
renamers += new RegexRenamer("""\s\s+""", " ")					// Removes duplicate spaces
renamers += new RegexRenamer("^([^-]+ - [^-]+) - .*", "$1")		// Removes everything after second '-'
renamers += new CapitalizeWithExceptionsRenamer(" ()", exs)		// Capitalizes all words (but exception)
renamers += new RegexRenamer(" And ", " & ")					// Shortens 'And'
renamers += new RegexRenamer("(Ft|Feat|Featuring)\\.?", "ft")	// Shortens 'Featuring'
renamers += new RegexRenamer("(.+) ft (.+) - (.+)", "$1 - $3 [ft $2]")	// Moves "ft" to the end
