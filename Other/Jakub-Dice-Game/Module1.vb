Module Module1
    Function Authenticator(name, password)

        Dim matchBool As Boolean
        Dim details() As String

        FileOpen(1, "player_list.csv", OpenMode.Input)
        While Not EOF(1)
            details = LineInput(1).Split(",")
            If details(0) = name And details(1) = password Then
                matchBool = 1
            End If
        End While

        FileClose(1)

        Return matchBool

    End Function
    Function DiceRoll(name)

        Dim num1, num2, score As Integer

        Randomize()
        num1 = Int((6 * Rnd()) + 1)
        num2 = Int((6 * Rnd()) + 1)

        If (num1 + num2) Mod 2 = 0 Then
            Console.WriteLine(name & " rolled an even number!")
            score += num1 + num2 + 10
        ElseIf (num1 + num2) Mod 2 = 1 Then
            Console.WriteLine(name & " rolled an odd number!")
            score += num1 + num2 - 5
        ElseIf num1 = num2 Then
            Console.WriteLine(name & " rolled a double!")
            score += num1 + num2 + Int((6 * Rnd()) + 1)
        End If

        If score < 0 Then
            score = 0
            Console.WriteLine(name & "'s score went below 0, so it's been reset to 0")
        End If

        Return score

    End Function
    Sub AddPlayer()
        Dim name, password As String

        Console.Write("Please enter the name of the new player: ")
        name = Console.ReadLine()
        Console.Write("Please enter the password: ")
        password = Console.ReadLine()

        Dim newData As String = name & "," & password

        FileOpen(3, "player_list.csv", OpenMode.Append)
        PrintLine(3, newData)
        FileClose(3)

        Console.WriteLine()
        Console.WriteLine(name & " added!")
        Console.WriteLine()
    End Sub
    Sub Main()

        Dim playerName, playerPass, p1, p2 As String
        Dim match, addPlayerFlag As Boolean

        Console.WriteLine("Welcome to The Dice Game!")
        Console.WriteLine("Each player rolls a dice and the winner is decided by a set of rules.")
        Console.WriteLine()
        Console.Write("Type anything to add a new player. Press enter to log in. ")

        If Not Console.ReadLine() = "" Then
            addPlayerFlag = True
        End If

        Console.WriteLine()

        If addPlayerFlag Then
            AddPlayer()
        End If

        Console.Write("Player 1, please enter your name: ")
        playerName = Console.ReadLine()
        p1 = playerName
        Console.Write("Player 1, please enter your password: ")
        playerPass = Console.ReadLine()
        Console.WriteLine()

        match = Authenticator(playerName, playerPass)

        If Not match Then
            Console.WriteLine("Sorry, " & playerName & ", but your name or password was incorrect.")
            Console.WriteLine("Press enter to exit.")
            Console.Read()
            Environment.Exit(0)
        End If

        Console.WriteLine("Welcome, " & playerName & ", to The Dice Game!")
        Console.WriteLine()



        Console.Write("Player 2, please enter your name: ")
        playerName = Console.ReadLine()
        p2 = playerName
        Console.Write("Player 2, please enter your password: ")
        playerPass = Console.ReadLine()
        Console.WriteLine()

        match = Authenticator(playerName, playerPass)

        If match = 0 Then
            Console.WriteLine("Sorry, " & playerName & ", but your name or password was incorrect.")
            Console.WriteLine("Press enter to exit.")
            Console.Read()
            Environment.Exit(0)
        End If

        Console.WriteLine("Welcome, " & playerName & ", to The Dice Game!")
        Console.WriteLine()
        Console.ReadLine()

        Dim p1Score, p2Score, winScore As Integer
        Dim winner As String

        For i = 1 To 5
            p1Score = DiceRoll(p1)
            p2Score = DiceRoll(p2)
            Console.WriteLine(p1 + "'s score is " & p1Score & " and " & p2 & "'s score is " & p2Score)

            Console.WriteLine("Press enter to continue.")
            Console.ReadLine()
            Console.WriteLine()
        Next i

        If p1Score = p2Score Then
            Console.WriteLine("It's a tie! Let's roll another die to determine the winner!")

            Randomize()
            Do
                p1Score += Int((6 * Rnd()) + 1)
                p2Score += Int((6 * Rnd()) + 1)
            Loop Until p1Score <> p2Score

            If p1Score > p2Score Then
                winner = p1
            Else
                winner = p2
            End If
            Console.WriteLine("The winner is " & winner & "!")
        Else
            If p1Score > p2Score Then
                winner = p1
                winScore = p1Score
            Else
                winner = p2
                winScore = p2Score
            End If
            Console.WriteLine("The winner is " & winner & "!")
        End If

        FileOpen(1, "scores.txt", OpenMode.Append)
        PrintLine(1, (winScore & " " & winner))
        FileClose(1)

        ' Get number of lines in scores.txt and set that as the length of allScores()
        Dim numLines As Integer = IO.File.ReadAllLines("scores.txt").Length
        Dim allScores(numLines) As String

        If numLines < 5 Then
            Console.WriteLine("There are not enough high scores to display them.")
        Else
            FileOpen(2, "scores.txt", OpenMode.Input)
            Dim count As Integer
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
            Console.ReadLine()

            For i = 0 To 4
                Console.WriteLine(allScores(i)) ' Write top five scores
            Next
        End If

        Console.WriteLine("Thank you for playing The Dice Game!")
        Console.WriteLine("Press enter to exit.")
        Console.Read()

    End Sub
End Module
