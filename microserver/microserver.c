#include <stdio.h>

int main()
{
	fprintf(stdout, "HTTP/1.1 200 OK\nContent-Type: text/html;charset=UTF-8\n\n<html>\n\n\t<body>\n\n\t\tBau bau, miao miao, cip cip.\n\n\t</body>\n\n</html>");
	return 0;
}

