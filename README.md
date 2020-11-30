# Bowling Score App

This program computes a bowling score given a data input with the player's rolls.

## Input
Each line of the input file must be on the format below:

```<player> <pinfalls>```

The player must be a string representing the player's name, containing no spaces, and the pinfalls must be a integer representing the number of pins knocked down on this roll.

Example:

```John 7```

The input must represent an entire game (10 frames) following the bowling rules and it can contain one or more players rolls.

Example:

```
John 7
John 3
Ann 10
John F
John 3
Ann 4
Ann 0
John 10
...
```

The character 'F' is used to represent a foul (e.g. line was crossed during throw).

## Usage

To execute the program, execute the jar file passing the input file as argument.

Example:

```
java -jar bowling-score-0.1.0-SNAPSHOT.jar ../src/main/resources/score-sample.txt
```

## Output

The output is given using tab-separated values.

Example:

```$xslt
Frame		1		2		3		4		5		6		7		8		9		10
Jeff
Pinfalls	  X		7 /		9 0		  X		0 8		8 /		F 6		  X		  X		  X 8 1		
Score		20		39		48		66		74		84		90		120		148		167		
John
Pinfalls	3 /		6 3		  X		8 1		  X		  X		9 0		7 /		4 4		  X 9 0		
Score		16		25		44		53		82		101		110		124		132		151
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)