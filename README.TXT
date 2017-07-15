This is a Java program for searching for a collection of words (or phrases) 
at the same time. This uses the Aho-Corasick algorithm.
See http://en.wikipedia.org/wiki/Aho-Corasick_algorithm

Ray Pereda
raypereda (at) gmail

$ java -jar multisearch.jar 
Usage: java -jar msearch.jar -p PATTERNFILENAME FILENAME1 FILENAME2 ...
Search for a list of fixed patterns in a list target files.
Example: java -jar multisearch.jar -f patterns.txt newarticle1.txt newsarticle2.txt

Required:
  must specify the patterns files with -f
  must specify at least one target filename


Suppose you have a list of phrases that identify things that you're interested in.
Put those phrases one per in a file. Here's an example file:

$ cat phrases-of-interests.txt 
chocolate
laptop
bicycle
caveman
paleo
simplify
genomics

Now suppose you have a list of news articles that you want to scan for all possible
matches of phrases that are interesting. Here are two example news articles.

$ cat newsarticle1.txt 
This article is about the latest in bicycle races.
In here we will review the latest in eliptical gears.

$ cat newsarticle2.txt 
This article is about Otzi. A caveman that lived about 10,000 years ago.
paleo-genomics leverage DNA to piece together Otzi's life.

Here's an example of multisearching for all the phrases in one pass through
the news articles:

java -jar multisearch.jar -p phrases-of-interests.txt newsarticle1.txt newsarticle2.txt 
target file: newsarticle1.txt
location: [    36,     43] matched: bicycle
target file: newsarticle2.txt
location: [    30,     37] matched: caveman
location: [    73,     78] matched: paleo
location: [    79,     87] matched: genomics


