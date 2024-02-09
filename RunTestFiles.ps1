# Путь к JAR-файлу
$jarPath = ".\target\Frames-1.0-SNAPSHOT.jar"

# Папка с тестовыми файлами
$testFilesFolder = ".\testFiles"

# Перебираем файлы от 1 до 75
for ($i = 1; $i -le 75; $i++) {
    # Формируем путь к текущему тестовому файлу
    $currentTestFile = Join-Path $testFilesFolder "$i"

    # Измеряем время выполнения
    $executionTime = Measure-Command {
        # Выполняем команду с использованием текущего тестового файла
        Get-Content $currentTestFile | java -jar $jarPath
    }

    # Записываем номер файла и время выполнения в файл
    "$i`t $($executionTime.TotalSeconds.ToString("F6"))" | Out-File -Append -FilePath "execution_times.txt"
}
