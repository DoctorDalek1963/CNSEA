Module Module1
    ' These are defined here to be used in different subroutines
    Dim p1ActiveCard, p2ActiveCard, p1Cards(0), p2Cards(0), p1Name, p2Name As String
    Sub Authenticate(ByVal name, ByVal password)
        Dim inputDetails, fileDetails As String
        Dim match As Boolean = False

        ' Format inputted name and password
        inputDetails = name & "," & password

        FileOpen(1, "player_list.csv", OpenMode.Input)

        ' For each line in file, test if correct
        While Not EOF(1)
            fileDetails = LineInput(1)
            If inputDetails = fileDetails Then
                match = True
            End If
        End While

        FileClose(1)

        ' If no match found, exit program
        If match = False Then
            Console.WriteLine("Sorry, " & name & ", but your name or password was incorrect.")
            Console.WriteLine("Press enter to exit")
            Console.ReadLine()
            Environment.Exit(0)
        End If

        Console.WriteLine("Welcome, " & name & ", to The Card Game!")
        Console.WriteLine()
    End Sub
    Sub CompareColours(ByVal colour1, ByVal colour2)
        Dim colour As String = colour1 & " " & colour2 ' Format colours properly
        Dim winColour As String

        ' Find winning colour
        Select Case colour

            Case "Red Black"
                winColour = "Red"
            Case "Black Red"
                winColour = "Red"

            Case "Yellow Red"
                winColour = "Yellow"
            Case "Red Yellow"
                winColour = "Yellow"

            Case "Black Yellow"
                winColour = "Black"
            Case "Yellow Black"
                winColour = "Black"

            Case Else
                winColour = 0
        End Select

        ' Find winner from colour
        If winColour = colour1 Then
            ReDim Preserve p1Cards(0 To UBound(p1Cards) + 2)
            p1Cards(UBound(p1Cards) - 1) = p1ActiveCard
            p1Cards(UBound(p1Cards)) = p2ActiveCard
            WinHand(p1Name)
        Else
            ReDim Preserve p2Cards(0 To UBound(p2Cards) + 2)
            p2Cards(UBound(p2Cards) - 1) = p1ActiveCard
            p2Cards(UBound(p2Cards)) = p2ActiveCard
            WinHand(p2Name)
        End If

    End Sub
    Sub WinHand(ByVal player)
        Console.WriteLine(player & " won that hand!")
        Console.WriteLine()
        Console.WriteLine("Press enter to continue")
        Console.ReadLine()
    End Sub
    Sub AddPlayer()
        Dim name, password, line As String

        ' Get details of new player and add them to player_list.csv
        Console.Write("Please enter the name of the new player: ")
        name = Console.ReadLine()
        Console.Write("Please enter the password: ")
        password = Console.ReadLine()

        FileOpen(3, "player_list.csv", OpenMode.Input)
        While Not EOF(3)
            line = LineInput(3)
            If name = line.Split(",")(0) Then
                Console.WriteLine()
                Console.WriteLine("That account already exists")
                Console.WriteLine()
                Return
            End If
        End While
        FileClose(3)

        Dim newData As String = name & "," & password

        FileOpen(3, "player_list.csv", OpenMode.Append)
        PrintLine(3, newData)
        FileClose(3)

        Console.WriteLine()
        Console.WriteLine(name & " added!")
        Console.WriteLine()
    End Sub
    Sub Login()
        Dim p1Pass, p2Pass As String

        Console.Write("Player 1, please enter your name: ")
        p1Name = Console.ReadLine()
        Console.Write("And your password: ")
        p1Pass = Console.ReadLine()
        Console.WriteLine()

        ' If authentication fails, the program exits, and if it succeeds, it welcomes the player
        Authenticate(p1Name, p1Pass)

        Console.Write("Player 2, please enter your name: ")
        p2Name = Console.ReadLine()
        Console.Write("And your password: ")
        p2Pass = Console.ReadLine()
        Console.WriteLine()

        ' Check that player 2 is on a different account
        If p1Name = p2Name Then
            Console.WriteLine("Sorry, player 2, but that's the same account as player 1.")
            Console.WriteLine("Press enter to exit")
            Console.ReadLine()
            Environment.Exit(0)
        End If

        Authenticate(p2Name, p2Pass)
    End Sub
    Sub Rules()
        Console.WriteLine("Welcome to The Card Game!")
        Console.WriteLine("This is a two player game.")
        Console.WriteLine("Each player draws a card, the cards are compared and the winner takes both cards.")
        Console.WriteLine("In total, 15 hands are drawn.")
        Console.WriteLine("The player with the most cards at the end wins.")
        Console.WriteLine()
    End Sub
    Sub HighScores()
        Dim count As Integer
        ' Get number of lines in scores.txt and set that as the length of allScores()
        Dim numLines As Integer = System.IO.File.ReadAllLines("scores.txt").Length
        Dim allScores(numLines) As String
        FileOpen(2, "scores.txt", OpenMode.Input)

        While Not EOF(2)
            LineInput(2)
            numLines += 1
        End While

        FileClose(2)

        If numLines < 5 Then
            Console.WriteLine("There are not enough high scores to display them")
        Else
            FileOpen(2, "scores.txt", OpenMode.Input)

            count = 0
            While Not EOF(2)
                ' Add each line of scores.txt to allScores()
                allScores(count) = LineInput(2)
                count += 1
            End While

            FileClose(2)

            Array.Sort(allScores) ' Sort the array alphanumerically
            Array.Reverse(allScores) ' Reverse the array to get it in the right order

            Console.WriteLine("These are the highscores: ")

            For i = 0 To 4
                Console.WriteLine(allScores(i)) ' Write top five scores
            Next
        End If

        Console.WriteLine()

    End Sub
    Sub Game()

        Dim deck() As String = System.IO.File.ReadAllLines("deck.txt")
        Dim count As Integer = 0

        Randomize() ' Initialise random

        ' This shuffler always keeps the last item in the same place but I'm too lazy to care
        Dim j As Integer
        Dim temp As String
        For i = 0 To 29
            j = Int((29 - i) * Rnd() + i)
            temp = deck(i)
            deck(i) = deck(j)
            deck(j) = temp
        Next

        Console.WriteLine("Let's begin!")
        Console.ReadLine()

        Dim handCount As Integer = 1

        For i = 0 To 29 Step 2 ' For every card in deck
            p1ActiveCard = deck(i)
            p2ActiveCard = deck(i + 1)
            Console.WriteLine("Hand " & handCount & ":")
            Console.WriteLine(p1Name & " drew a " & p1ActiveCard)
            Console.WriteLine(p2Name & " drew a " & p2ActiveCard)
            Console.WriteLine()

            Dim p1Colour, p2Colour, p1Number, p2Number As String

            p1Colour = p1ActiveCard.Split(" ")(0)
            p2Colour = p2ActiveCard.Split(" ")(0)

            If p1Colour = p2Colour Then
                ' If same colour, highest number wins
                p1Number = Int(p1ActiveCard.Split(" ")(1))
                p2Number = Int(p2ActiveCard.Split(" ")(1))

                If p1Number > p2Number Then
                    ReDim Preserve p1Cards(0 To UBound(p1Cards) + 2)
                    p1Cards(UBound(p1Cards) - 1) = p1ActiveCard
                    p1Cards(UBound(p1Cards)) = p2ActiveCard
                    WinHand(p1Name)
                Else
                    ReDim Preserve p2Cards(0 To UBound(p2Cards) + 2)
                    p2Cards(UBound(p2Cards) - 1) = p1ActiveCard
                    p2Cards(UBound(p2Cards)) = p2ActiveCard
                    WinHand(p2Name)
                End If
            Else
                CompareColours(p1Colour, p2Colour)
            End If

            handCount += 1
        Next

        Console.WriteLine("All cards have been drawn!")
        Console.WriteLine()
        Console.WriteLine("The winner is ...")
        Console.ReadLine()

        Dim winner, winCards() As String
        Dim winNum As Integer

        ' Compare lengths of card stacks to find winner (person with most cards)
        If p1Cards.Length > p2Cards.Length Then
            winner = p1Name
            winCards = p1Cards
        Else
            winner = p2Name
            winCards = p2Cards
        End If

        winNum = winCards.Length - 1

        Console.WriteLine(winner & " with " & winNum & " cards!")
        Console.WriteLine()
        Console.WriteLine("They had these cards: ")
        ' Console.WriteLine()

        For Each card In winCards
            Console.WriteLine(card)
        Next

        FileOpen(2, "scores.txt", OpenMode.Append)

        Dim score As String = winNum & " " & winner
        PrintLine(2, score)

        FileClose(2)

        Console.ReadLine()
    End Sub
    Sub Main()
        Dim choice As String = ""
        Do
            Console.WriteLine("***** Menu *****")
            Console.WriteLine("Enter a choice:")
            Console.WriteLine("1. Add Player")
            Console.WriteLine("2. Login")
            Console.WriteLine("3. Rules")
            Console.WriteLine("4. High Scores")
            Console.WriteLine("5. Play Game")
            Console.WriteLine("6. Exit")
            Console.WriteLine()
            choice = Console.ReadLine()
            Console.WriteLine()

            Select Case choice
                Case ""
                    Continue Do
                Case "1"
                    AddPlayer()
                Case "2"
                    Login()
                Case "3"
                    Rules()
                Case "4"
                    HighScores()
                Case "5"
                    If p1Name <> "" And p2Name <> "" Then
                        Game()
                    Else
                        Console.WriteLine("You must login first")
                        Console.WriteLine()
                        Continue Do ' Loop and show menu again
                    End If
                Case "6"
                    Environment.Exit(0)
                Case Else
                    Console.WriteLine("Invalid choice")
                    Console.WriteLine()
            End Select
        Loop

    End Sub
End Module
