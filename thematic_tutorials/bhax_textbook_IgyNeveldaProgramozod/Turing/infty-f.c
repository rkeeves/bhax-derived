#ifdef __linux__
  #include <unistd.h>
#elif _WIN32
  #include <windows.h>
#else
  #error Unsupported
#endif

void SleepProxy(int sleepMs)
{
#ifdef __linux__
    usleep(sleepMs * 1000);
#elif _WIN32
    Sleep(sleepMs);
#endif
}

int
main ()
{
  for (;;)
    SleepProxy(1000);
    
  return 0;
}
