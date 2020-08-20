Module Module1
    Function Authenticate(name, password)
        Dim details As String

        details = name & "," & password
        FileOpen(0, "player_list.csv", OpenMode.Output)



    End Function
    Sub Main()
        Console.WriteLine(CurDir)

        Console.Read()
    End Sub
End Module
