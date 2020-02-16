#ifdef __linux__ 
#include <unistd.h>
#include <term.h>
void ClearScreen(){
if (!cur_term)
  {
  int result;
  setupterm( NULL, STDOUT_FILENO, &result );
  if (result <= 0) return;
  }

  putp( tigetstr( "clear" ) );
}
void sleep(unsigned milliseconds){
    usleep(milliseconds * 1000);
}
#elif _WIN32
#include <windows.h>
void ClearScreen(){
  HANDLE                     hStdOut;
  CONSOLE_SCREEN_BUFFER_INFO csbi;
  DWORD                      count;
  DWORD                      cellCount;
  COORD                      homeCoords = { 0, 0 };

  hStdOut = GetStdHandle( STD_OUTPUT_HANDLE );
  if (hStdOut == INVALID_HANDLE_VALUE) return;

  if (!GetConsoleScreenBufferInfo( hStdOut, &csbi )) return;
  cellCount = csbi.dwSize.X *csbi.dwSize.Y;

  if (!FillConsoleOutputCharacter(
    hStdOut,
    (TCHAR) ' ',
    cellCount,
    homeCoords,
    &count
    )) return;

  if (!FillConsoleOutputAttribute(
    hStdOut,
    csbi.wAttributes,
    cellCount,
    homeCoords,
    &count
    )) return;

  SetConsoleCursorPosition( hStdOut, homeCoords );
}

void sleep(unsigned milliseconds){
    Sleep(milliseconds);
}
#else
  #error OS_NOT_SUPPORTED;
#endif