
// Add the renamers you want to renamers : ListBuffer[SimpleFileRenamer]
// Use optional args : List[String]

// Here the args is the 'regex' to find and the 'replacement'
val List(regex,replacement) = args

renamers += new RegexRenamer(regex, replacement)
