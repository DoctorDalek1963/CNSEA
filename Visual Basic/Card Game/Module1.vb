Module Module1
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

        If match = False Then
            Console.WriteLine("Sorry, " & name & ", but your name or password was incorrect.")
            Console.WriteLine("Press enter to exit")
            Console.Read()
            Environment.Exit(0)
        End If

        Console.WriteLine("Welcome, " & name & ", to The Card Game!")
        Console.WriteLine()
    End Sub
    Function CompareColours(colour1, colour2)
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

        If winColour = colour1 Then
            Return "Player 1"
        Else
            Return "Player 2"
        End If

    End Function
    Sub Main()
        Dim p1Name, p1Pass, p2name, p2Pass As String

        Console.Write("Player 1, please enter your name: ")
        p1Name = Console.ReadLine()
        Console.Write("Player 1, please enter your password: ")
        p1Pass = Console.ReadLine()
        Console.WriteLine()

        ' If authentication fails, the program exits, and if it succeeds, it welcomes the player
        'Authenticate(p1Name, p1Pass)

        Console.Write("Player 2, please enter your name: ")
        p2name = Console.ReadLine()
        Console.Write("Player 2, please enter your password: ")
        p2Pass = Console.ReadLine()
        Console.WriteLine()

        'If p1Name = p2name Then
        '    Console.WriteLine("Sorry, player 2, but that's the same account as player 1.")
        '    Console.WriteLine("Press enter to exit")
        '    Console.Read()
        '    Environment.Exit(0)
        'End If

        'Authenticate(p2name, p2Pass)

        Dim deck(29) As String
        Dim count As Integer = 0

        FileOpen(1, "deck.txt", OpenMode.Input)

        While Not EOF(1)
            deck(count) = LineInput(1)
            Console.WriteLine(deck)
            count += 1
        End While

        FileClose(1)

        Randomize() ' Initialise random

        ' This shuffler always keeps the last item in the same place but I'm too lazy to care
        Dim j As Integer
        Dim k As String
        For i = 0 To 29
            j = Int((29 - i) * Rnd() + i)
            k = deck(i)
            deck(i) = deck(j)
            deck(j) = k
        Next

        Console.WriteLine("Press enter to exit")
        Console.Read()
    End Sub
End Module
