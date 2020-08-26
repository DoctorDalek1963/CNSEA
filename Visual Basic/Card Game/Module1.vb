Module Module1
    ' These are defined here to be used in different subroutines
    Dim p1ActiveCard, p2ActiveCard, p1Cards(), p2Cards(), p1Name, p2Name As String
    Sub Authenticate(name, password)
        Dim inputDetails, fileDetails As String
        Dim match As Boolean = False

        ' Format inputted name and password
        inputDetails = name & "," & password

        FileOpen(0, "player_list.csv", OpenMode.Input)

        ' For each line in file, test if correct
        While Not EOF(0)
            fileDetails = LineInput(0)
            If inputDetails = fileDetails Then
                match = True
            End If
        End While

        FileClose(0)

        ' If no match found, exit program
        If match = False Then
            Console.WriteLine("Sorry, " & name & ", but your name or password was incorrect.")
            Console.WriteLine("Press enter to exit")
            Console.Read()
            Environment.Exit(0)
        End If

        Console.WriteLine("Welcome, " & name & ", to The Card Game!")
        Console.WriteLine()
    End Sub
    Sub CompareColours(colour1, colour2)
        Dim colour As String = colour1 & "," & colour2 ' Format colours properly
        Dim winColour As String

        ' Find winning colour
        Select Case colour
            Case "Red Black" Or "Black Red"
                winColour = "Red"

            Case "Yellow Red" Or "Red Yellow"
                winColour = "Yellow"

            Case "Black Yellow" Or "Yellow Black"
                winColour = "Black"

            Case Else
                winColour = 0
        End Select

        ' Find winner from colour
        If winColour = colour1 Then
            WinHand(p1Cards, p1Name)
        Else
            WinHand(p2Cards, p2Name)
        End If

    End Sub
    Sub WinHand(ByRef cardList, player)
        ' Append both active cards to winner's card stack
        cardList.Add(p1ActiveCard)
        cardList.Add(p2ActiveCard)
        Console.WriteLine(player & " won that hand!")
        Console.WriteLine()
        Console.WriteLine("Press enter to continue")
        Console.Read()
        Console.WriteLine()
    End Sub
    Sub Main()
        Dim p1Pass, p2Pass As String

        Console.Write("Player 1, please enter your name: ")
        p1Name = Console.ReadLine()
        Console.Write("Player 1, please enter your password: ")
        p1Pass = Console.ReadLine()
        Console.WriteLine()

        ' If authentication fails, the program exits, and if it succeeds, it welcomes the player
        Authenticate(p1Name, p1Pass)

        Console.Write("Player 2, please enter your name: ")
        p2name = Console.ReadLine()
        Console.Write("Player 2, please enter your password: ")
        p2Pass = Console.ReadLine()
        Console.WriteLine()

        ' Check that player 2 is on a different account
        If p1Name = p2Name Then
            Console.WriteLine("Sorry, player 2, but that's the same account as player 1.")
            Console.WriteLine("Press enter to exit")
            Console.Read()
            Environment.Exit(0)
        End If

        Authenticate(p2Name, p2Pass)

        Dim deck(29) As String
        Dim count As Integer = 0

        FileOpen(1, "deck.txt", OpenMode.Input)

        While Not EOF(1)
            ' Add each line of deck.txt to deck()
            deck(count) = LineInput(1)
            count += 1
        End While

        FileClose(1)

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
        Console.Read()
        Console.WriteLine()

        For i = 0 To 29 Step 2 ' For every card in deck
            p1ActiveCard = deck(i)
            p2ActiveCard = deck(i + 1)
            Console.WriteLine(p1Name & " drew a " & p1ActiveCard)
            Console.WriteLine(p2Name & " drew a " & p2ActiveCard)
            Console.WriteLine("Press enter to continue")
            Console.Read()
            Console.WriteLine()

            Dim p1Colour, p2Colour, p1Number, p2Number As String

            p1Colour = p1ActiveCard.Split(" ")(0)
            p2Colour = p2ActiveCard.Split(" ")(0)

            If p1Colour = p2Colour Then
                ' If same colour, highest number wins
                p1Number = Int(p1ActiveCard.Split(" ")(1))
                p2Number = Int(p2ActiveCard.Split(" ")(1))

                If p1Number > p2Number Then
                    WinHand(p1Cards, p1Name)
                Else
                    WinHand(p2Cards, p2Name)
                End If
            Else
                CompareColours(p1Colour, p2Colour)
            End If

        Next

        Console.WriteLine("All cards have been drawn!")
        Console.WriteLine("The winner is ...")
        Console.Read()

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
        winNum = winCards.Length

        Console.WriteLine(winner & " with " & winNum & " cards!")

        Console.WriteLine("They had these cards: ")
        Console.Read()

        For Each card In winCards
            Console.WriteLine(card)
        Next
        Console.WriteLine()

        FileOpen(2, "scores.txt", OpenMode.Append)

        Dim score As String = winNum & " " & winner
        PrintLine(2, score)

        Dim allScores() As String
        count = 0
        While Not EOF(2)
            ' Add each line of scores.txt to allScores()
            allScores(count) = LineInput(2)
            count += 1
        End While

        Array.Sort(allScores) ' Sort the array alphanumerically

        FileClose(2)

        Console.WriteLine("These are the highscores: ")
        Console.Read()

        For i = 0 To 4
            Console.WriteLine(allScores(i)) ' Write top five scores
        Next

        Console.WriteLine("Thank you, " & p1Name & " and " & p2Name & ", for playing The Card Game!")
        Console.WriteLine("Press enter to exit")
        Console.Read()
    End Sub
End Module
