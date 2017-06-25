package cz.improvisio.qton.car.filters

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Osa-S on 25.06.2017.
 */
@Component
class SimpleCORSFilter implements Filter {
	private final Logger log = LoggerFactory.getLogger(SimpleCORSFilter.class)

	SimpleCORSFilter() {
		log.info("SimpleCORSFilter init")
	}

	@Override
	void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req
		HttpServletResponse response = (HttpServletResponse) res

		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"))
		response.setHeader("Access-Control-Allow-Credentials", "true")
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
		response.setHeader("Access-Control-Max-Age", "3600")
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me")

		chain.doFilter(req, res)
	}

	@Override
	void init(FilterConfig filterConfig) {
	}

	@Override
	void destroy() {
	}
}
